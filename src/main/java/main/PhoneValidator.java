package main;

import main.exceptions.ValidationException;
import main.rules.PhoneNumberRule;

import java.util.ArrayList;

public class PhoneValidator {
    private final ArrayList<PhoneNumberRule> rules = new ArrayList<>();

    private String errorMessage = null;

    public boolean checkPhoneNumber(String countryCode, String phoneNumber) {
        this.errorMessage = null;

        try {
            PhoneNumberRule rule = this.getPhoneNumberRule(countryCode);

            if (rule == null) {
                throw new ValidationException("Šios šalies telefono numeris nėra palaikomas");
            }

            String fullPhoneNumber = changePhoneNumberToFull(countryCode, phoneNumber);
            char[] prefixArray = rule.getFullPrefix().toCharArray();
            int index = 0, prefixLength = prefixArray.length, countryNumberLength = rule.getLength();

            for (char symbol : fullPhoneNumber.toCharArray()) {
                if (index < prefixLength && symbol != prefixArray[index]) {
                    throw new ValidationException("Telefono numerio pradžia neatitinka pasirinktos šalies");
                }

                index++;
            }

            if (index != countryNumberLength) {
                throw new ValidationException("Šalies telefono numerio ilgis turi būti " + countryNumberLength + " simbolių");
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

    public String changePhoneNumberToFull(String countryCode, String phoneNumber) {
        PhoneNumberRule rule = this.getPhoneNumberRule(countryCode);

        if (rule == null) {
            return phoneNumber;
        }

        String shortPrefix = rule.getShortPrefix();

        if (shortPrefix == null) {
            return phoneNumber;
        }

        int shortPrefixLength = shortPrefix.length(), index = 0;
        char[] shortPrefixArray = shortPrefix.toCharArray();
        StringBuilder newNumber = new StringBuilder(rule.getFullPrefix());

        for (char symbol : phoneNumber.toCharArray()) {
            if (index < shortPrefixLength) {
                if (symbol != shortPrefixArray[index]) {
                    return phoneNumber;
                }
            } else {
                newNumber.append(symbol);
            }
            index++;
        }

        return newNumber.toString();
    }

    public boolean addNewCountryRule(String countryCode, int length, String prefix) {
        return this.addNewCountryRule(countryCode, length, prefix, null);
    }

    public boolean addNewCountryRule(String countryCode, int length, String prefix, String shortCode) {
        if (this.getPhoneNumberRule(countryCode) != null) {
            return false;
        }

        PhoneNumberRule rule = new PhoneNumberRule(countryCode, length, prefix, shortCode);
        this.rules.add(rule);

        return true;
    }

    private PhoneNumberRule getPhoneNumberRule(String countryCode) {
        int ruleIndex = rules.indexOf(new PhoneNumberRule(countryCode));

        if (ruleIndex != -1) {
            return rules.get(ruleIndex);
        } else {
            return null;
        }
    }
}

