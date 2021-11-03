package com.psp;

import com.psp.exceptions.ValidationException;

import java.util.ArrayList;

public class PasswordChecker {
    private final ArrayList<Character> specialSymbols = new ArrayList<>();

    private String errorMessage = null;

    public boolean checkPassword(String password, int minLength){
        int length = 0;
        boolean hasSpecialSymbol = false, hasUppercaseSymbol = false;
        this.errorMessage = null;

        try {
            for (char symbol : password.toCharArray()) {
                if (symbol >= 'A' && symbol <= 'Z') {
                    hasUppercaseSymbol = true;
                } else if (!(symbol >= 'a' && symbol <= 'z') && !(symbol >= '0' && symbol <= '9')) {
                    checkIfSpecialSymbol(symbol);
                    hasSpecialSymbol = true;
                }

                length++;
            }

            if (length < minLength) {
                throw new ValidationException("Per trumpas slaptažodis. Minimalus ilgis - " + minLength + " simboliai");
            }

            if (!hasSpecialSymbol) {
                throw new ValidationException("Slaptažodis privalo turėti bent vieną specialų simbolį");
            }

            if (!hasUppercaseSymbol) {
                throw new ValidationException("Slaptažodis privalo turėti bent vieną didžiąją raidę");
            }
        } catch (ValidationException exception) {
            this.errorMessage = exception.getMessage();

            return false;
        }

        return true;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getSpecialSymbols() {
        return this.specialSymbols.toString();
    }

    public boolean addSpecialSymbol(char symbol) {
        if (this.specialSymbols.contains(symbol)) {
            return false;
        }

        this.specialSymbols.add(symbol);

        return true;
    }

    public boolean addSpecialSymbol(char[] symbols) {
        boolean result = false;

        for (char symbol : symbols) {
            if (this.addSpecialSymbol(symbol)) {
                result = true;
            }
        }

        return result;
    }

    private void checkIfSpecialSymbol(char symbol) throws ValidationException {
        if (this.specialSymbols.contains(symbol)) {
            return;
        }

        throw new ValidationException("Slaptažodis turi neleistiną specialų simbolį");
    }
}

