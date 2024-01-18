package ke.co.nectar.token.domain.tokensubclass.class2;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class ClearCreditTokenSubClass extends TokenSubClass {

    public ClearCreditTokenSubClass() throws InvalidRangeException {
        super(0x1L, "ClearCreditTokenSubClass");
    }
}
