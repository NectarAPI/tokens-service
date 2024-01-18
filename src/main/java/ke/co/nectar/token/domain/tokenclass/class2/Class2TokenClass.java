package ke.co.nectar.token.domain.tokenclass.class2;

import ke.co.nectar.token.domain.tokenclass.TokenClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public abstract class Class2TokenClass extends TokenClass {

    public Class2TokenClass(String name) throws InvalidRangeException {
        super(0x2l, name);
    }
}
