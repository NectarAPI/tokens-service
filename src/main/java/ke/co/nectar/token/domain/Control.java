package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidControlBitStringException;
import ke.co.nectar.token.miscellaneous.Strings;

public class Control implements Entity {

    private BitString controlBitString;
    private ManufacturerCode manufacturerCode;
    private final String NAME = "Control";

    public Control(BitString controlBitString, ManufacturerCode manufacturerCode)
        throws InvalidControlBitStringException {
        setManufacturerCode(manufacturerCode);
        setControlBitString(controlBitString);
    }

    public String getName() {
        return NAME;
    }

    public BitString getBitString() {
        return controlBitString;
    }

    public void setControlBitString(BitString controlBitString)
        throws InvalidControlBitStringException {
        if (manufacturerCode.getBitString().getLength() == 8 && controlBitString.getLength() == 36)
            this.controlBitString = controlBitString;
        else if (manufacturerCode.getBitString().getLength() == 16 && controlBitString.getLength() == 28)
            this.controlBitString = controlBitString;
        else
            throw new InvalidControlBitStringException(Strings.INVALID_CONTROL_BIT_STRING);
    }

    private ManufacturerCode getManufacturerCode() {
        return manufacturerCode;
    }

    private void setManufacturerCode(ManufacturerCode manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }
}
