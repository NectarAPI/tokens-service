package ke.co.nectar.token.domain.tokensubclass.class2;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class ClearTamperConditionTokenSubClass extends TokenSubClass {

    public ClearTamperConditionTokenSubClass() throws InvalidRangeException {
        super(0x5L, "ClearTamperConditionTokenSubClass");
    }
}
