package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.PayLoad.Request.VerficationRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface VerificationService {

    void sendEmail(VerficationRequest VerifyRequest, HttpServletRequest request);
    void verify(String token);

}
