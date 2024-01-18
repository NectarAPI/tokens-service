package ke.co.nectar.token.domain.tokensubclass.class2;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class SetTariffRateTokenSubClass extends TokenSubClass {

    public SetTariffRateTokenSubClass() throws InvalidRangeException {
        super(0x2L, "SetTariffRateTokenSubClass");
    }
}
