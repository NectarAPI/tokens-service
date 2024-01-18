package ke.co.nectar.token.domain.tokensubclass.class0;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class GasCreditTransferTokenSubClass extends TokenSubClass {

    public GasCreditTransferTokenSubClass() throws InvalidRangeException {
        super(0x2L, "Gas");
    }
}
