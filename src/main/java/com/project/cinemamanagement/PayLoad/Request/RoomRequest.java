package com.project.cinemamanagement.PayLoad.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Enum.ROOMSTAT;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    @NotBlank(message = "status must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ROOMSTAT status;
    @NotBlank(message = "RoomType must not be blank")
    @Pattern(regexp = "^(Standard|Premium)$", message = "RoomType must be Standard or Premium")
    private String RoomType;
}
