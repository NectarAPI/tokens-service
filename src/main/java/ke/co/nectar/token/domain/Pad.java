package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidPadException;
import ke.co.nectar.token.miscellaneous.Strings;

public class Pad implements Entity {

    private final String NAME = "Pad";
    private BitString padBitString ;

    public Pad(BitString padBitString)
        throws InvalidPadException {
        setBitString(padBitString);
    }

    public String getName() {
        return NAME;
    }

    public String getNAME() {
        return NAME;
    }

    public BitString getBitString() {
        return padBitString;
    }

    public void setBitString(BitString padBitString)
        throws InvalidPadException {
        if (padBitString.getLength() != 16)
            throw new InvalidPadException(Strings.INVALID_PAD_EXCEPTION);
        this.padBitString = padBitString;
    }
}
