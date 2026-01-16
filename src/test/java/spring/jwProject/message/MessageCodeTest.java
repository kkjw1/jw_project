package spring.jwProject.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodeTest {

    MessageCodesResolver ms = new DefaultMessageCodesResolver();

    @Test
    @DisplayName("메시지 코드 생성")
    void messageCode() {
        String[] messageCodes = ms.resolveMessageCodes("CheckPWError", "Member", "memberId", String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
    }

}
