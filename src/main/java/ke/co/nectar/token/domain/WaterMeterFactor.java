package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidWMFException;
import ke.co.nectar.token.miscellaneous.Strings;

public class WaterMeterFactor implements Entity {

    public static final String NAME = "WaterMeterFactor";
    private BitString wmfBitString ;

    public WaterMeterFactor(BitString wmfBitString)
        throws InvalidWMFException {
        setWmfBitString(wmfBitString);
    }

    public String getName() {
        return NAME;
    }

    public BitString getWmfBitString() {
        return wmfBitString;
    }

    public void setWmfBitString(BitString wmfBitString)
        throws InvalidWMFException {
        if(wmfBitString.getLength() != 16)
            throw new InvalidWMFException(Strings.INVALID_WMF);
        this.wmfBitString = wmfBitString;
    }
}
