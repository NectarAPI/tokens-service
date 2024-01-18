package ke.co.nectar.token.domain.rate;

import ke.co.nectar.token.domain.Entity;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.miscellaneous.Strings;

public class Rate implements Entity {

    public final String NAME = "Rate";
    private BitString rateBitString ;

    public Rate(BitString rateBitString)
        throws InvalidRateException {
        setRateBitString(rateBitString);
    }

    public String getName() {
        return NAME;
    }


    public BitString getRateBitString() {
        return rateBitString;
    }

    public void setRateBitString(BitString rateBitString)
        throws InvalidRateException {
        if(rateBitString.getLength() != 16)
            throw new InvalidRateException(Strings.INVALID_RATE);
        this.rateBitString = rateBitString;
    }

    @Override
    public String toString() {
        return rateBitString.toString();
    }
}
