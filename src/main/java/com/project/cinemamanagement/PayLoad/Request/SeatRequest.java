package com.project.cinemamanagement.PayLoad.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Enum.SEATSTAT;
import com.project.cinemamanagement.Enum.SEATTYPE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {
    @NotBlank(message = "SeatNumber must not be blank")
    @Pattern(regexp = "^(1[0-9]|20|[1-9])([A-T])(_(1[0-9]|20|[1-9])\\2)?$", message = "Seat number is incorrect")
    private String SeatNumber;
    @NotNull(message = "SeatType must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private SEATTYPE SeatType;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private SEATSTAT SeatStatus;
    private Long RoomId;
}
