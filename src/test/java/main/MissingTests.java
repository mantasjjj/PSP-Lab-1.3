package main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MissingTests {
    public final EmailValidator emailValidator = new EmailValidator();
    public final PasswordChecker passwordChecker = new PasswordChecker();
    public final PhoneValidator phoneValidator = new PhoneValidator();

    @BeforeAll
    void setup() {
        phoneValidator.addNewCountryRule("LT", 12, "+370", "8");
    }

    // Email validator
    @Test
    void emailDoesntHaveAtSymbol() {
        assertFalse(emailValidator.checkEmail("emailemail.com"));
    }

    @Test
    void emailHasMoreAtSymbols() {
        assertFalse(emailValidator.checkEmail("ema@ile@mail.com"));
    }

    @Test
    void emailHasTwoDotsInARow() {
        assertFalse(emailValidator.checkEmail("email@email..com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a”b(c)d,e:f;gi[j\\k]l@domain.com", "a”dasd@domain.com", "asdb(c)dFl@domain.com", "agi;jk:l@domain.com"})
    void emailHasIllegalSymbols(String email) {
        assertFalse(emailValidator.checkEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email@subdomain.c0m", "email@subdomain.čom"})
    void emailHasIllegalTLD(String email) {
        assertFalse(emailValidator.checkEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email@-subdomain.com", "email@subdoma!n.com"})
    void emailHasIllegalDomain(String email) {
        assertFalse(emailValidator.checkEmail(email));
    }

    // Password checker
    @Test
    void passwordTooShortTest() {
        passwordChecker.addSpecialSymbol('*');
        assertFalse(passwordChecker.checkPassword("Passw*d", 8));
    }

    // Phone validator
    @Test
    void phoneNumberTooShortTest() {
        assertFalse(phoneValidator.checkPhoneNumber("LT", "+3704681351"));
    }

    @Test
    void phoneNumberTooLongTest() {
        assertFalse(phoneValidator.checkPhoneNumber("LT", "+370468135123"));
    }

    @Test
    void phoneNumberPrefixDoesntMatchTest() {
        assertFalse(phoneValidator.checkPhoneNumber("LT", "+371468135123"));
    }
}

