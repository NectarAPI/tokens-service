package ke.co.nectar.token.domain.tokensubclass.class2;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class SetMaximumPhasePowerUnbalanceLimitTokenSubClass extends TokenSubClass {

    public SetMaximumPhasePowerUnbalanceLimitTokenSubClass() throws InvalidRangeException {
        super(0x6L, "SetMaximumPhasePowerUnbalanceLimitTokenSubClass");
    }
}
