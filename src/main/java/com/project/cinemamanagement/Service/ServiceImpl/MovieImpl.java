package com.project.cinemamanagement.Service.ServiceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cinemamanagement.Entity.Image;
import com.project.cinemamanagement.Entity.Movie;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Exception.FailedToUploadException;
import com.project.cinemamanagement.Exception.InternalErrorException;
import com.project.cinemamanagement.Exception.InvalidDataException;
import com.project.cinemamanagement.PayLoad.Request.MovieRequest;
import com.project.cinemamanagement.PayLoad.Request.MovieShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Request.ShowtimeRequest;
import com.project.cinemamanagement.PayLoad.Response.MovieResponse;
import com.project.cinemamanagement.PayLoad.Response.MovieShowtimeResponse;
import com.project.cinemamanagement.PayLoad.Response.ShowtimeResponse;
import com.project.cinemamanagement.Repository.ImageRepository;
import com.project.cinemamanagement.Repository.MovieRepository;
import com.project.cinemamanagement.Service.MovieService;
import com.project.cinemamanagement.Specifications.MovieSpecifications;
import com.project.cinemamanagement.Service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final ShowTimeService showTimeService;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final int MAX_RETRY = 3;

    private final ImageRepository imageRepository;

    @Override
    public List<MovieResponse> getAllMovie() {
        List<Movie> movieList = movieRepository.findAllByOrderByMovieId();
        movieList.removeIf(movie -> movie.getEndDate().before(new Date()));
        List<MovieResponse> movieResponses = new ArrayList<>();
        for (Movie movie : movieList) {
            movieResponses.add(new MovieResponse(movie));
        }
        return movieResponses;

    }

    @Override
    public MovieResponse addMovie(MovieRequest movie) throws IOException {
        if (isValidMovie(movie)) {
            return null;
        }

        String imageUrl = "";
        if (movie.getImage() != null) {
            File file = saveTemporaryImage(movie.getImage(), movie.getMovieName().replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", "_"));
            imageUrl = updateToCloud(file, movie.getMovieName());
        }
//        deleteTemnaryImage(file);

        Movie newMovie = new Movie.Builder()
                .setMovieName(movie.getMovieName())
                .setMovieGenre(movie.getMovieGenre())
                .setDescription(movie.getDescription())
                .setDuration(movie.getDuration())
                .setDirector(movie.getDirector())
                .setActor(movie.getActor())
                .setReleaseDate(movie.getReleaseDate())
                .setEndDate(movie.getEndDate())
                .setAgeRestriction(movie.getAgeRestriction())
                .setUrlTrailer(movie.getUrlTrailer())
                .setUrlThumbnail(imageUrl)
                .build();

        //Do upload image to cloudinary
        movieRepository.save(newMovie);
        return new MovieResponse(newMovie);

    }

    private boolean isValidMovie(MovieRequest movieRequest) {
        if (movieRequest.getReleaseDate().after(movieRequest.getEndDate()) || movieRequest.getReleaseDate().equals(movieRequest.getEndDate()))
            throw new InvalidDataException("release date should be less than end date");
        return false;
    }

    @Override
    public MovieShowtimeResponse getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));
        List<ShowtimeResponse> showtimeResponseList = showTimeService.getShowTimeByMovieId(movieId);
        showtimeResponseList.removeIf(showtimeResponse -> showtimeResponse.getTimeEnd().before(new Date()));
        return new MovieShowtimeResponse(movie, showtimeResponseList);
    }

    @Override
    public MovieResponse updateMovie(Long movieId, MovieRequest movie) throws IOException {
        Movie updateMovie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));
        Optional<Image> image = imageRepository.findByUrl(updateMovie.getUrlThumbnail());

        if (isValidMovie(movie)) {
            return null;
        }

        String imageUrl = updateMovie.getUrlThumbnail();
        if (movie.getImage() != null) {
            File file = saveTemporaryImage(movie.getImage(), movie.getMovieName().replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", "_"));
            imageUrl = updateToCloud(file, movie.getMovieName());
            if (image.isPresent()) {
                delteFromCloud(image.get().getPublicId());
                imageRepository.delete(image.get());
            }
        }
//        updateMovie.setMovieName(movie.getMovieName());
//        updateMovie.setMovieGenre(movie.getMovieGenre());
//        updateMovie.setDescription(movie.getDescription());
//        updateMovie.setDuration(movie.getDuration());
//        updateMovie.setDirector(movie.getDirector());
//        updateMovie.setActor(movie.getActor());
//        updateMovie.setReleaseDate(movie.getReleaseDate());
//        updateMovie.setEndDate(movie.getEndDate());
//        updateMovie.setAgeRestriction(movie.getAgeRestriction());
//        updateMovie.setUrlTrailer(movie.getUrlTrailer());

        BeanUtils.copyProperties(movie, updateMovie);
        updateMovie.setUrlThumbnail(imageUrl);

        movieRepository.save(updateMovie);
        return new MovieResponse(updateMovie);
    }

    @Override
    public MovieResponse deleteMovie(Long movieId) throws IOException {
        Movie deleteMovie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie not found"));
        Optional<Image> image = imageRepository.findByUrl(deleteMovie.getUrlThumbnail());

        if (image.isPresent()) {
            delteFromCloud(image.get().getPublicId());
            imageRepository.delete(image.get());
        }

        movieRepository.delete(deleteMovie);

        return new MovieResponse(deleteMovie);
    }

    @Override
    public List<MovieResponse> getMovieByDate(Date date) {
        Specification<Movie> spec = MovieSpecifications.GetMovieByDate(date);

        List<Movie> movies = movieRepository.findAll(spec);
        if (movies.isEmpty()) {
            throw new DataNotFoundException("Movie not found");
        }

        List<MovieResponse> movieResponses = new ArrayList<>();
        for (Movie movie : movies) {
            movieResponses.add(new MovieResponse(movie));
        }
        return movieResponses;
    }

    @Override
    public void addMovieShowtime(MovieShowtimeRequest movieShowtimeRequest) throws JsonProcessingException {

        MovieRequest movie = new MovieRequest();
        movie.setMovieName(movieShowtimeRequest.getMovieName());
        movie.setMovieGenre(movieShowtimeRequest.getMovieGenre());
        movie.setDescription(movieShowtimeRequest.getDescription());
        movie.setDuration(movieShowtimeRequest.getDuration());
        movie.setDirector(movieShowtimeRequest.getDirector());
        movie.setActor(movieShowtimeRequest.getActor());
        movie.setReleaseDate(movieShowtimeRequest.getReleaseDate());
        movie.setEndDate(movieShowtimeRequest.getEndDate());
        movie.setAgeRestriction(movieShowtimeRequest.getAgeRestriction());
        movie.setUrlTrailer(movieShowtimeRequest.getUrlTrailer());
        movie.setImage(movieShowtimeRequest.getImage());
        try {
            addMovie(movie);
        } catch (IOException e) {
            throw new InternalErrorException(e.getMessage());
        }
        System.out.println(movieShowtimeRequest.getListShowtime());
        List<ShowtimeRequest> showtimeList = objectMapper.readValue(
                movieShowtimeRequest.getListShowtime(), new TypeReference<List<ShowtimeRequest>>() {
                });
        if (showtimeList == null) return;
        Movie movie1 = movieRepository.findByMovieName(movieShowtimeRequest.getMovieName()).orElseThrow(() -> new DataNotFoundException("Movie not found"));
        showTimeService.addShowTimeList(showtimeList, movie1);
    }

    @Override
    public File saveTemporaryImage(MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            throw new InvalidDataException("File cannot be empty");
        }

//        long fileSize = file.getSize();
//        String contentType = file.getContentType();
        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            file.transferTo(filePath.toFile());
            return filePath.toFile();
        } catch (IOException e) {
            throw new InternalErrorException(e.getMessage());
        }
    }


    @Override
    public String updateToCloud(File file, String movieName) throws IOException {
        int retry = 0;

        Cloudinary cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret,
                        "secure", true
                )
        );

        Map params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", true,
                "overwrite", true,
                "folder", "MovieCinema",
                "resource_type", "image",
                "quality", "auto",
                "context", "alt=For " + movieName + "|caption=" + movieName + " Thumbnail",
                "format", "jpg"
//                "tags", "profile, user"


        );
        String url = "";
        while (retry < MAX_RETRY) {
            try {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(file, params);
                String publicID = (String) uploadResult.get("public_id");
                url = (String) uploadResult.get("url");
                Image image = new Image();
                image.setUrl(url);
                image.setPublicId(publicID);
                imageRepository.save(image);
                deleteTemnaryImage(file);
                return url;
            } catch (IOException e) {
                retry++;
//                System.err.println("Upload failed on attempt " + retry + ": " + e.getMessage());
                if (retry == MAX_RETRY) {
                    deleteTemnaryImage(file);
                    throw new FailedToUploadException("Upload failed after " + MAX_RETRY + " attempts");
                }

            }
        }

//        for (Map.Entry<?, ?> entry : uploadResult.entrySet()) {
//            System.out.format("%s: %s\n", entry.getKey(), entry.getValue());
//        }
        return url;
    }

    @Override
    public void deleteTemnaryImage(File file) {
        Path filePath = Paths.get(UPLOAD_DIR, file.getName());
        try {
            if (!Files.exists(filePath)) {
                return;
            }
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override
    public void delteFromCloud(String publicId) throws IOException {
        Cloudinary cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret,
                        "secure", true
                )
        );
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
