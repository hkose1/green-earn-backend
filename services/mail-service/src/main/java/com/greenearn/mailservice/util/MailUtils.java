package com.greenearn.mailservice.util;

public class MailUtils {

    public static String getLinkAccountVerificationEmailMessage(String name, String host, String key) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click on the link below to verify your account.\n\n" +
                getVerificationUrl(host, key) + "\n\nThe Support Team";
    }

    public static String getCodeAccountVerificationEmailMessage(String name, String code) {
        return "Hello " + name + ",\n\n" +
                "Your new account has been created. Please use the following 6-digit code to verify your account:\n\n" +
                "Verification Code: " + code + "\n\n" +
                "This code will expire in 30 minutes.\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The Support Team";
    }


    private static String getVerificationUrl(String host, String key) {
        return host + "/api/auth/verify/account?key=" + key;
    }
}
