package com.project.cinemamanagement.PayLoad.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    @NotBlank(message = "price must not be blank")
    private Long price;
    @NotBlank(message = "seatLocation must not be blank")
    private String[] seatLocation;
    @NotBlank(message = "date must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    @NotBlank(message = "showtimeId must not be blank")
    private Long showtimeId;
    @NotBlank(message = "userId must not be blank")
    private Long userId;
}
