package com.project.cinemamanagement.PayLoad.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    @NotNull(message = "seatLocation must not be null")
    @Size(min = 1, message = "seatLocation must contain at least one element")
    private String[] seatLocation;
    @NotNull(message = "date must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date date;
    @Positive(message = "showtimeId is incorrect")
    private Long showtimeId;
    @Positive(message = "showtimeId is incorrect")
    private Long userId;

}
