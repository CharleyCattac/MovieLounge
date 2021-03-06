package com.lobach.movielounge.validator;

import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.model.UserStatus;

public class UserValidator {
    private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private static final String PASS_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{5,15}$";
    private static final String NAME_REGEX = "(?=^.{0,40}$)^[a-zа-яA-ZА-Я-]+\\s[a-zа-яA-ZА-Я-]+$";
    private static final String PHONE_NUMBER_REGEX = "^[0-9]{11,12}$";

    private UserValidator() {}

    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASS_REGEX);
    }

    public static boolean validateName(String name) {
        return name.matches(NAME_REGEX);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public static boolean validateRole(String roleString) {
        for (UserRole role : UserRole.values()) {
            if (role.value.equals(roleString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateStatus(String statusString) {
        for (UserStatus status : UserStatus.values()) {
            if (status.value.equals(statusString)) {
                return true;
            }
        }
        return false;
    }
}
