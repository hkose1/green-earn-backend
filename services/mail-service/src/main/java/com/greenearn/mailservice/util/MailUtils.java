package com.greenearn.mailservice.util;

import com.greenearn.mailservice.dto.SendChallengeCompletedDto;

public class MailUtils {

    public static String getChallengeCompletedEmailMessage(SendChallengeCompletedDto mailDto) {
        return "Hello " + mailDto.getName() + ",\n\n" +
                "Congratulations on completing the challenge: \"" + mailDto.getChallengeTitle() + "\"!\n\n" +
                mailDto.getChallengeDescription() + "\n\n" +
                "You've earned " + mailDto.getEarnedPointsFromChallenge() + " points for your effort.\n" +
                "Keep up the great work and continue pushing your limits!\n\n" +
                "Stay motivated,\n" +
                "The Green Earn Team";
    }

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

    public static String getCodeResetPasswordEmailMessage(String name, String code) {
        return "Hello " + name + ",\n\n" +
                "You requested to reset your password. Please use the following 6-digit code to reset your password:\n\n" +
                "Security Code: " + code + "\n\n" +
                "This code will expire in 30 minutes.\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The Support Team";
    }


    private static String getVerificationUrl(String host, String key) {
        return host + "/api/auth/verify/account?key=" + key;
    }
}
