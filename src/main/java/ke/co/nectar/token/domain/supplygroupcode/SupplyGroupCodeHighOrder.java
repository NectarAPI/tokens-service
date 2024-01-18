package ke.co.nectar.token.domain.supplygroupcode;

import ke.co.nectar.token.domain.Entity;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidBitStringException;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.exceptions.InvalidSgchoException;
import ke.co.nectar.token.miscellaneous.Strings;

public class SupplyGroupCodeHighOrder implements Entity {

    private final String NAME = "SupplyGroupCodeHighOrder" ;
    private BitString sgchoBitString;
    private SupplyGroupCode supplyGroupCode;

    public SupplyGroupCodeHighOrder(SupplyGroupCode supplyGroupCode)
            throws InvalidSgchoException, InvalidBitStringException, InvalidRangeException {
        if (!supplyGroupCode.getValue().matches("[0-9]{6}"))
            throw new InvalidSgchoException(Strings.INVALID_SGCHO_EXCEPTION) ;
        String supplyGroupBitString = Integer.toBinaryString(Integer.parseInt(supplyGroupCode.getValue()));
        BitString sgchoBitString = new BitString(("000000000000000000000000"
                                    + supplyGroupBitString).substring(supplyGroupBitString.length()).substring(0, 12));
        sgchoBitString.setLength(12);
        setBitString(sgchoBitString);
        setSupplyGroupCode(supplyGroupCode);
    }

    public SupplyGroupCodeHighOrder(BitString sgchoBitString)
        throws InvalidSgchoException {
        setBitString(sgchoBitString);
    }

    public String getName() {
        return NAME;
    }

    public BitString getBitString() {
        return sgchoBitString;
    }

    public void setBitString(BitString sgchoBitString)
        throws InvalidSgchoException {
        if (sgchoBitString.getLength() != 12)
            throw new InvalidSgchoException(Strings.INVALID_SGCHO_EXCEPTION);
        this.sgchoBitString = sgchoBitString;
    }

    public SupplyGroupCode getSupplyGroupCode() {
        return supplyGroupCode;
    }

    public void setSupplyGroupCode(SupplyGroupCode supplyGroupCode) {
        this.supplyGroupCode = supplyGroupCode;
    }
}
