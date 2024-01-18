package ke.co.nectar.token.domain.tokensubclass.class0;

import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;

public class WaterCreditTransferTokenSubClass extends TokenSubClass {

    public WaterCreditTransferTokenSubClass() throws InvalidRangeException {
        super(0x1L, "Water");
    }
}
