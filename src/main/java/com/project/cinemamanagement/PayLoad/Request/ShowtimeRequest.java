package com.project.cinemamanagement.PayLoad.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeRequest
{
    @NotBlank(message = "timeStart must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;
    @NotBlank(message = "timeEnd must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;
    @NotBlank(message = "status must not be blank")
    private int status;
    @NotBlank(message = "movieId must not be blank")
    private Long movieId;
    @NotBlank(message = "roomId must not be blank")
    private Long roomId;
}
