package spring.jwProject.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    @DisplayName("수정한 내용만 Map에 담기")
    public void makeMap() throws Exception {
        TestObject t1 = new TestObject(1L, "t1", "t1", "t1");
        TestObject t2 = new TestObject(1L, "update", "update", "t1");

        System.out.println(t1);
        System.out.println(t2);
        Field[] test = t1.getClass().getDeclaredFields();

        Map<String, List<String>> result = new HashMap<>();
        for (Field field : test) {
            field.setAccessible(true); // private 필드 접근
            if (!field.get(t1).equals(field.get(t2))) {
                List<String> l1 = new ArrayList<>();
                l1.add((String) field.get(t1));
                l1.add((String) field.get(t2));
                result.put(field.getName(), l1);
            }
        }

        System.out.println(result);

        for (String s : result.keySet()) {
            System.out.println(s + ": " + result.get(s));
        }

    }

    public static class TestObject {
        private Long id;
        private String name;
        private String phoneNumber;
        private String price;

        public TestObject(Long id, String name, String phoneNumber, String price) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.price = price;
        }
    }
    
    @Test
    @DisplayName("test")
    public void tttest() throws Exception {
    }

}
