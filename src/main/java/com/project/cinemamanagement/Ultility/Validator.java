package com.project.cinemamanagement.Ultility;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class Validator {
    // Các loại MIME hợp lệ
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg");
    // Kích thước tối đa (tính theo byte, ví dụ: 2MB)
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    public static boolean isValidFile(MultipartFile file) {
        if (file.isEmpty()) {
            return false; // File rỗng
        }

        // Kiểm tra loại file
        String contentType = file.getContentType();
        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            return false; // Loại file không hợp lệ
        }

        // Kiểm tra kích thước file
        long fileSize = file.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            return false; // File quá lớn
        }

        return true; // File hợp lệ
    }
}
