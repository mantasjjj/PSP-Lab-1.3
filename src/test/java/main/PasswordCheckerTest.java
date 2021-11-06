package main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class PasswordCheckerTest {
    private PasswordChecker passwordChecker;
    @BeforeAll
    void setup(){
        passwordChecker = new PasswordChecker();
    }
    //Tests for password
    @Test
    void passwordLengthIsNotLessThanXTest(){
        assertFalse(passwordChecker.checkPassword("password", 8));
    }
    @Test
    void passwordHasUppercaseSymbolsTest(){
        assertFalse(passwordChecker.checkPassword("Password",8));
    }

    @Test
    void addSymbolToSpecialListTest() {
        passwordChecker.addSpecialSymbol('*');
        assertTrue(passwordChecker.checkPassword("Pass*word",8));
    }

    @Test
    void passwordHasSpecialSymbolTest(){
        assertFalse(passwordChecker.checkPassword("Pass!word",8));
    }
}
