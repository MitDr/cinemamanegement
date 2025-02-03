package com.project.cinemamanagement.PayLoad.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @NotBlank(message = "paymentMethod must not be blank")
    @Pattern(regexp = "^(Cash|Stripe)$", message = "paymentMethod must be Cash or Credit or Debit")
    private String paymentMethod;
    @NotBlank(message = "paymentStatus must not be blank")
    private String paymentStatus;
    @NotBlank(message = "paymentDate must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent
    private Date paymentDate;
    @NotBlank(message = "paymentPrice must not be blank")
    private Long paymentPrice;
    @NotNull(message = "ticketId must not be null")
    @Size(min = 1, message = "ticketId must contain at least one element")
    private Long[] ticketId;
    @NotBlank(message = "userId must not be blank")
    private Long userId;
}
