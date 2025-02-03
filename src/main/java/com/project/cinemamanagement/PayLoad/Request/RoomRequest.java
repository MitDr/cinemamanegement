package com.project.cinemamanagement.PayLoad.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinemamanagement.Enum.ROOMSTAT;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.regex.qual.Regex;

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
