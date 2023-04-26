package com.carrot.mail;

import com.carrot.common.response.MyAppRespons;
import org.springframework.http.ResponseEntity;

public class MailService {
    public ResponseEntity<MyAppRespons> sendFindPasswordMail(String email) {
        throw new RuntimeException("impl not yet");
    }

    public ResponseEntity<MyAppRespons> checkFindPasswordCode(String uuid) {
        throw new RuntimeException("impl not yet");
    }

    public ResponseEntity<MyAppRespons> resetPassword(String uuid, PasswordResetRequest passwordResetRequest) {
        throw new RuntimeException("impl not yet");
    }

    public ResponseEntity<InviteCodeResponse> checkInviteWorkspaceCode(String uuid) {
        throw new RuntimeException("impl not yet");
    }
}
