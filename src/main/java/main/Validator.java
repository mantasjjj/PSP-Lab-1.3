package main;

import main.rules.PhoneNumberRule;

import java.util.List;

public class Validator {
    public int minPasswordLength;

    EmailValidator emailValidator = new EmailValidator();
    PasswordChecker passwordChecker = new PasswordChecker();
    PhoneValidator phoneValidator = new PhoneValidator();

    public Validator(char[] specialCharacters, int minPasswordLength, List<PhoneNumberRule> rules) {
        this.minPasswordLength = minPasswordLength;
        passwordChecker.addSpecialSymbol(specialCharacters);
        rules.forEach(rule -> phoneValidator.addNewCountryRule(rule.getCountryCode(), rule.getLength(), rule.getFullPrefix()));
    }

    public boolean isEmailValid(String email) {
        return emailValidator.checkEmail(email);
    }

    public boolean isPasswordValid(String password) {
        return passwordChecker.checkPassword(password, minPasswordLength);
    }

    public boolean isPhoneNumberValid(String countryCode, String phoneNumber) {
        return phoneValidator.checkPhoneNumber(countryCode, phoneNumber);
    }
}
