package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidNKLOException;
import ke.co.nectar.token.miscellaneous.Strings;

public class NewKeyLowOrder implements Entity {

    public final String NAME = "NewKeyLowOrder";
    private BitString nkloBitString;

    public NewKeyLowOrder(BitString nkloBitString)
        throws InvalidNKLOException {
        setNkloBitString(nkloBitString);
    }

    public String getName() {
        return NAME;
    }

    public BitString getNkloBitString() {
        return nkloBitString;
    }

    public void setNkloBitString(BitString nkloBitString)
        throws InvalidNKLOException {
        if (nkloBitString.getLength() != 32)
            throw new InvalidNKLOException(Strings.INVALID_NKLO);
        this.nkloBitString = nkloBitString;
    }
}
