package main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailValidatorTest {
    private EmailValidator emailValidator;

    @BeforeAll
    void setup(){
        emailValidator = new EmailValidator();
    }

    //Tests for email
    @Test
    void emailHasEtaSymbolTest(){
        assertTrue(emailValidator.checkEmail("email@gmail.com"));
    }
    @Test
    void emailHasIllegalSymbolsTest(){
        assertTrue(emailValidator.checkEmail("email@gmail.com"));
    }

    @Test
    void emailUsernameLengthIsCorrectTest(){
        assertTrue(emailValidator.checkEmail("emaillll@gmail.com"));
    }

    @Test
    void emailDomainLengthIsCorrectTest(){
        assertTrue(emailValidator.checkEmail("email@gmail.com"));
    }

    @Test
    void emailDomainIsCorrectTest(){
        assertTrue(emailValidator.checkEmail("email@gmail.com"));
    }

    @Test
    void emailHasCorrectTopLevelDomainTest(){
        assertTrue(emailValidator.checkEmail("email@gmail.com"));
    }
}

