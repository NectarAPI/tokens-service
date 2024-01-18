package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidKeyRevisionNumber;
import ke.co.nectar.token.miscellaneous.Strings;

public class KeyRevisionNumber implements Entity {

    private int keyRevisionNumber;
    private final String NAME = "Key Revision Number";

    public KeyRevisionNumber(int keyRevisionNo)
            throws InvalidKeyRevisionNumber {
        setValue(keyRevisionNo);
    }

    public int getValue() {
        return keyRevisionNumber;
    }

    public void setValue(int keyRevisionNumber)
            throws InvalidKeyRevisionNumber {
        if (keyRevisionNumber < 1 || keyRevisionNumber > 9)
            throw new InvalidKeyRevisionNumber(Strings.INVALID_KEY_REVISION_NUMBER);
        this.keyRevisionNumber = keyRevisionNumber;
    }

    public String getName() {
        return NAME;
    }

    public String toString() {
        return String.valueOf(keyRevisionNumber);
    }
}
