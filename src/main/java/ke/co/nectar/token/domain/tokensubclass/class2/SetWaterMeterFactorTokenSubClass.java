package ke.co.nectar.token.domain.tokensubclass.class2;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class SetWaterMeterFactorTokenSubClass extends TokenSubClass {

    public SetWaterMeterFactorTokenSubClass() throws InvalidRangeException {
        super(0x7L, "SetWaterMeterFactorTokenSubClass");
    }
}
