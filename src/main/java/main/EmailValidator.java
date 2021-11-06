package main;

import main.exceptions.ValidationException;

public class EmailValidator {
    private String errorMessage = null;

    public boolean checkEmail(String email) {
        boolean isNamePart = true, isFirst = true, isLegalInTLD = true, wasLastSymbolADot = false;
        this.errorMessage = null;

        try {
            for (char c : email.toCharArray()) {
                if (c == '@') {
                    if (isNamePart) {
                        isNamePart = false;
                    } else {
                        throw new ValidationException("Email should contain only ONE @ symbol");
                    }
                } else {
                    if (isNamePart) {
                        isLegalSymbolInNamePart(c);
                    } else {
                        if (c == '.') {
                            if (isFirst) {
                                throw new ValidationException("Domain cannot start with dot");
                            }
                            if (wasLastSymbolADot) {
                                throw new ValidationException("Domain cannot have two dots in a row");
                            }

                            isLegalInTLD = true;
                            wasLastSymbolADot = true;
                        } else {
                            if (c == '-') {
                                if (isFirst || wasLastSymbolADot) {
                                    throw new ValidationException("Domain part cannot start with this symbol: -");
                                }
                            } else {
                                if (c >= '0' && c <= '9') {
                                    isLegalInTLD = false;
                                }
                                isFirst = false;
                            }
                            wasLastSymbolADot = false;
                        }

                        isLegalSymbolInDomainPart(c);
                    }
                }
            }
            if (isNamePart) {
                throw new ValidationException("You forgot the @ symbol");
            }
            if (!isLegalInTLD) {
                throw new ValidationException("Top level domain should only contain these symbols a-z, A-Z");
            }
            if (isFirst) {
                throw new ValidationException("Missing domain part or ends with dot");
            }

            return true;
        } catch (ValidationException exception) {
            this.errorMessage = exception.getMessage();

            return false;
        }
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    private void isLegalSymbolInNamePart(char symbol) throws ValidationException {
        char[] legalSymbols = new char[] {
                '!', '#', '$', '%', '&', '’', '*', '+', '-', '/', '=', '?', '^', '_', '{', '|', '\'', '}', '~', ',', '.'
        };

        if ((symbol >= 'a' && symbol <= 'z') || (symbol >= 'A' && symbol <= 'Z') || (symbol >= '0' && symbol <= '9')) {
            return;
        }

        for (char legalSymbol : legalSymbols) {
            if (symbol == legalSymbol) {
                return;
            }
        }

        throw new ValidationException("Illegal symbol in name part of email: " + symbol);
    }

    private void isLegalSymbolInDomainPart(char symbol) throws ValidationException {
        if ((symbol >= 'a' && symbol <= 'z') || (symbol >= 'A' && symbol <= 'Z') || (symbol >= '0' && symbol <= '9')
                || symbol == '.' || symbol == '-') {
            return;
        }

        throw new ValidationException("Illegal symbol in name part of email: " + symbol);
    }
}

