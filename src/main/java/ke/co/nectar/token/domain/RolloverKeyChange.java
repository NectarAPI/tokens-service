package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidRollOverKeyChangeException;
import ke.co.nectar.token.miscellaneous.Strings;

public class RolloverKeyChange implements Entity {

    public final String NAME = "Roll Over Key Change (RO)";
    private BitString rollOverKeyChange;

    public RolloverKeyChange(BitString rollOverKeyChange)
        throws InvalidRollOverKeyChangeException {
        setBitString(rollOverKeyChange);
    }

    public String getName() {
        return NAME;
    }

    public BitString getBitString() {
        return rollOverKeyChange;
    }

    public void setBitString(BitString rollOverKeyChange)
        throws InvalidRollOverKeyChangeException {
        if(rollOverKeyChange.getLength() != 1)
            throw new InvalidRollOverKeyChangeException(Strings.INVALID_ROLL_OVER_KEY_CHANGE);
        this.rollOverKeyChange = rollOverKeyChange;
    }
}
