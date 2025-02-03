package com.project.cinemamanagement.Constant;

public interface SecurityConstants {
    String[] ADMIN_API_PATH = {
            "/api/v1/admin/**",
    };

    String[] CLIENT_API_PATH = {
            "/api/v1/client/**",
    };

    String[] IGNORING_API_PATH = {
            "/api/v1/auth/**",
            "/api/v1/public/**"
    };

    interface Role {
        String ADMIN = "ADMIN";
        String USER = "USER";
    }
}
