package com.project.cinemamanagement.MyResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse  {
private Object message;
private int httpStatus;
}
