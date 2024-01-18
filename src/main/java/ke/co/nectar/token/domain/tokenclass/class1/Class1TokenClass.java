package ke.co.nectar.token.domain.tokenclass.class1;

import ke.co.nectar.token.domain.tokenclass.TokenClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public abstract class Class1TokenClass extends TokenClass {

    public Class1TokenClass(String name) throws InvalidRangeException {
        super(0x1l, name);
    }
}
