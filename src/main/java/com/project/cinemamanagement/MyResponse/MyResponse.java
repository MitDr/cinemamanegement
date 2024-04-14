package com.project.cinemamanagement.MyResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResponse {
    private Object data;
    private String message;
    private int httpStatus;
}
