package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1;

import ke.co.nectar.token.domain.Control;
import ke.co.nectar.token.domain.Crc;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class1.Class1Token;
import ke.co.nectar.token.exceptions.BitConcatOverflowError;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.TokenGenerator;

public abstract class Class1TokenGenerator extends TokenGenerator<Class1Token> {

    protected Control control;
    protected ManufacturerCode manufacturerCode;

    public Class1TokenGenerator(String requestID, Control control, ManufacturerCode manufacturerCode) {
        super(requestID);
        setControl(control);
        setManufacturerCode(manufacturerCode);
    }

    @Override
    public BitString generate64BitDataBlock(Class1Token token)
            throws BitConcatOverflowError {

        BitString tokenClass = token.getTokenClass().getBitString();
        BitString tokenSubClass = token.getTokenSubClass().getBitString();
        BitString control = token.getControl().getBitString();
        BitString mfrCode = token.getManufacturerCode().getBitString();
        BitString crc = new Crc().generateCRC(mfrCode.concat(control, tokenSubClass, tokenClass));
        BitString _64BitDataBlock = crc.concat(mfrCode, control, tokenSubClass);
        return _64BitDataBlock;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public ManufacturerCode getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(ManufacturerCode manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }
}
