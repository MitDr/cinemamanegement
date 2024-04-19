package com.project.cinemamanagement.MyResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    private Object message;
    private int httpsStatus;
}
