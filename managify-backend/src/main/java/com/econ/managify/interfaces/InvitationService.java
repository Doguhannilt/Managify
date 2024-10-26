package com.econ.managify.interfaces;

import com.econ.managify.model.Invitation;

public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws Exception;
    public Invitation acceptInvitation(String token, Long userId) throws Exception;
    public String getTokenByUserMail(String userEmail);
    void deleteToken(String token);
}
