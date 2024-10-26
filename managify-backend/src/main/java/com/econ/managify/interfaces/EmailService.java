package com.econ.managify.interfaces;



public interface EmailService {
    void sendEmailWithToken(String userEmail, String link) throws Exception;
}
