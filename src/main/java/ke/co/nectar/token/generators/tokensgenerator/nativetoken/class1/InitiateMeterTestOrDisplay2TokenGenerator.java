package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1;

import ke.co.nectar.token.domain.Control;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay2Token;
import ke.co.nectar.token.domain.tokenclass.class1.InitiateMeterTestDisplayTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class1.InitiateMeterTestDisplay2TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidControlException;
import ke.co.nectar.token.exceptions.InvalidManufacturerCodeException;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.miscellaneous.Strings;

public class InitiateMeterTestOrDisplay2TokenGenerator extends Class1TokenGenerator {

    private InitiateMeterTestDisplay2TokenSubClass tokenSubClass;

    public InitiateMeterTestOrDisplay2TokenGenerator(String requestID,
                                                     Control control,
                                                     ManufacturerCode manufacturerCode)
            throws InvalidRangeException, InvalidControlException, InvalidManufacturerCodeException {
        super(requestID, control, manufacturerCode);
        if (manufacturerCode.getBitString().getLength() != 16) {
            if (control.getBitString().getLength() != 28) {
                throw new InvalidControlException(Strings.INVALID_CONTROL);
            }
            throw new InvalidManufacturerCodeException(Strings.INVALID_MANUFACTURER_CODE);
        }
        tokenSubClass = new InitiateMeterTestDisplay2TokenSubClass();
    }

    public InitiateMeterTestOrDisplay2Token generate() throws Exception {
        InitiateMeterTestDisplayTokenClass tokenClass = new InitiateMeterTestDisplayTokenClass();
        InitiateMeterTestOrDisplay2Token token = new InitiateMeterTestOrDisplay2Token(requestID,
                                                                                        tokenClass,
                                                                                        tokenSubClass,
                                                                                        control,
                                                                                        manufacturerCode);
        BitString _64BitDataBlock = generate64BitDataBlock(token);
        String transposed66BitString = transpose66BitString(tokenClass, _64BitDataBlock);
        token.setEncryptedTokenBitString(transposed66BitString);

        return token;
    }
}
