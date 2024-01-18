package ke.co.nectar.token.domain.tokensubclass.class2;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class SetMaximumPowerLimitTokenSubClass extends TokenSubClass {

    public SetMaximumPowerLimitTokenSubClass() throws InvalidRangeException {
        super(0x0L, "SetMaximumPowerLimitTokenSubClass");
    }
}
