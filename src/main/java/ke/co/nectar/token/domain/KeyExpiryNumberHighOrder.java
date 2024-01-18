package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidKenhoException;
import ke.co.nectar.token.miscellaneous.Strings;

public class KeyExpiryNumberHighOrder implements Entity {

    private final String NAME = "SupplyGroupCodeHighOrder" ;
    private BitString kenhoBitString;

    public KeyExpiryNumberHighOrder(BitString kenhoBitString)
        throws InvalidKenhoException {
        setBitString(kenhoBitString);
    }

    public String getName() {
        return getNAME();
    }


    public String getNAME() {
        return NAME;
    }

    public BitString getBitString() {
        return kenhoBitString;
    }

    public void setBitString(BitString kenhoBitString)
        throws InvalidKenhoException {
        if (kenhoBitString.getLength() != 4)
            throw new InvalidKenhoException(Strings.INVALID_KENHO_EXCEPTION);
        this.kenhoBitString = kenhoBitString;
    }
}
