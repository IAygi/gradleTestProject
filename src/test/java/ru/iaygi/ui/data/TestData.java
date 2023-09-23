package ru.iaygi.ui.data;

public class TestData {
    public static final String phoneNumber = "8-911-111-11-11";
    public static final String Email = "mail@mail.com";
    public static final String message = "Test message";
    public static final String checkOrder = "Ваш заказ на сумму 430 ₽ сформирован!";
    public static final String userLogin = System.getProperty("user_login", "selenide");
    public static final String userPass = System.getProperty("user_pass", "junit5");
    public static boolean enableVNC = setVnc();

    private static boolean setVnc() {
        String enableVnc = System.getProperty("vnc_enabled", "true");
        if (enableVnc.equals("false")) {
            return false;
        }
        return true;
    }
}
