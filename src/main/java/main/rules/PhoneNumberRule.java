package main.rules;

import java.util.Objects;

public class PhoneNumberRule {
    private String countryCode;

    private String prefix;

    private int length;

    private String shortPrefix;

    // For ArrayList check
    public PhoneNumberRule(String countryCode) {
        this.countryCode = countryCode;
    }

    public PhoneNumberRule(String countryCode, int length, String prefix) {
        this.countryCode = countryCode;
        this.prefix = prefix;
        this.length = length;
    }

    public PhoneNumberRule(String countryCode, int length, String prefix, String shortPrefix) {
        this(countryCode, length, prefix);
        this.shortPrefix = shortPrefix;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getLength() {
        return this.length;
    }

    public String getFullPrefix() {
        return this.prefix;
    }

    public String getShortPrefix() {
        return this.shortPrefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneNumberRule that = (PhoneNumberRule) o;

        return countryCode.equals(that.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode);
    }
}
