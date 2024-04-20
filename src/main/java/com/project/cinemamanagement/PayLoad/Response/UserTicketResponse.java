package com.project.cinemamanagement.PayLoad.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketResponse {
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private List<TicketResponse> tickets;
}
