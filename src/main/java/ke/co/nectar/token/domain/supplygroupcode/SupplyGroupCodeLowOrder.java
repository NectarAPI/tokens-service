package ke.co.nectar.token.domain.supplygroupcode;

import ke.co.nectar.token.domain.Entity;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidBitStringException;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.exceptions.InvalidSgcloException;
import ke.co.nectar.token.miscellaneous.Strings;

public class SupplyGroupCodeLowOrder implements Entity {

    public final String NAME = "SupplyGroupCodeLowOrder";
    private BitString sgcloBitString;
    private SupplyGroupCode supplyGroupCode;

    public SupplyGroupCodeLowOrder(SupplyGroupCode supplyGroupCode)
            throws InvalidSgcloException, InvalidBitStringException, InvalidRangeException {
        if (!supplyGroupCode.getValue().matches("[0-9]{6}"))
            throw new InvalidSgcloException(Strings.INVALID_SGCLO_EXCEPTION) ;
        String supplyGroupBitString = Integer.toBinaryString(Integer.parseInt(supplyGroupCode.getValue()));
        BitString sgcloBitString = new BitString(("000000000000000000000000"
                                    + supplyGroupBitString).substring(supplyGroupBitString.length()).substring(12, 24));
        sgcloBitString.setLength(12);
        setBitString(sgcloBitString);
        setSupplyGroupCode(supplyGroupCode);
    }

    public SupplyGroupCodeLowOrder(BitString sgcloBitString)
        throws InvalidSgcloException {
        setBitString(sgcloBitString);
    }

    public BitString getSgcloBitString() {
        return sgcloBitString;
    }

    public void setBitString(BitString sgcloBitString)
        throws InvalidSgcloException {
        if (sgcloBitString.getLength() != 12)
            throw new InvalidSgcloException(Strings.INVALID_SGCLO_EXCEPTION) ;
        this.sgcloBitString = sgcloBitString;
    }

    public SupplyGroupCode getSupplyGroupCode() {
        return supplyGroupCode;
    }

    public void setSupplyGroupCode(SupplyGroupCode supplyGroupCode) {
        this.supplyGroupCode = supplyGroupCode;
    }

    public String getName() {
        return NAME;
    }
}
