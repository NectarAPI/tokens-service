package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidIssuerIdentificationNumberException;
import ke.co.nectar.token.miscellaneous.Strings;

public class IssuerIdentificationNumber implements Entity {

    private String issuerIdentificationNumberValue;
    private final String NAME = "Issuer Identification Number";

    public IssuerIdentificationNumber(String issuerIdentificationNumberValue)
        throws InvalidIssuerIdentificationNumberException {
        setValue(issuerIdentificationNumberValue);
    }

    public String getName() {
        return NAME;
    }

    public String getValue() {
        return issuerIdentificationNumberValue;
    }

    public void setValue(String issuerIdentificationNumberValue)
        throws InvalidIssuerIdentificationNumberException {
        if (!issuerIdentificationNumberValue.matches("[0-9]{6}") &&
                !issuerIdentificationNumberValue.matches("[0]{4}"))
            throw new InvalidIssuerIdentificationNumberException(String.format(Strings.INVALID_ISSUER_IDENTIFICATION_NUMBER)) ;

        this.issuerIdentificationNumberValue = issuerIdentificationNumberValue;
    }
}
