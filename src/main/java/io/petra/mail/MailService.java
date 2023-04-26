package io.petra.mail;

import io.petra.common.response.MyAppRespons;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
