package main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class PhoneValidatorTest {
    private PhoneValidator phoneValidator;
    @BeforeAll
    void setup(){
        phoneValidator = new PhoneValidator();
        phoneValidator.addNewCountryRule("LT", 12, "+370", "8");
    }

    //Tests for phone number
    @Test
    void phoneNumberHasOnlyCorrectSymbolsAndNumbersTest(){
        assertTrue(phoneValidator.checkPhoneNumber("LT", "+37062478759"));
    }

    @Test
    void phoneNumberStartsWithEightChangeTest() {
        assertEquals("+37062542736", phoneValidator.changePhoneNumberToFull("LT","862542736"));
    }

    @Test
    void addNewCountryRuleTest() {
        phoneValidator.addNewCountryRule("EE", 11, "+372");
        assertTrue(phoneValidator.checkPhoneNumber("EE", "+3724564781"));
    }

    @Test
    void differentCountryPhoneNumberButWithoutRuleTest(){
        assertFalse(phoneValidator.checkPhoneNumber("LV", "+37162542837"));
    }


}
