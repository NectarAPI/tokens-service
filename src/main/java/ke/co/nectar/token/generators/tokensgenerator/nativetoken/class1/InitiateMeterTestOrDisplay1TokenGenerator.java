package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1;

import ke.co.nectar.token.domain.Control;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay1Token;
import ke.co.nectar.token.domain.tokenclass.class1.InitiateMeterTestDisplayTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class1.InitiateMeterTestDisplay1TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidControlException;
import ke.co.nectar.token.exceptions.InvalidManufacturerCodeException;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.miscellaneous.Strings;

public class InitiateMeterTestOrDisplay1TokenGenerator extends Class1TokenGenerator {

    private InitiateMeterTestDisplay1TokenSubClass tokenSubClass;

    public InitiateMeterTestOrDisplay1TokenGenerator(String requestID,
                                                     Control control,
                                                     ManufacturerCode manufacturerCode)
            throws InvalidRangeException, InvalidManufacturerCodeException, InvalidControlException {
        super(requestID, control, manufacturerCode);
        if (manufacturerCode.getBitString().getLength() != 8) {
            if (control.getBitString().getLength() != 36) {
                throw new InvalidControlException(Strings.INVALID_CONTROL);
            }
            throw new InvalidManufacturerCodeException(Strings.INVALID_MANUFACTURER_CODE);
        }
        tokenSubClass = new InitiateMeterTestDisplay1TokenSubClass();
    }

    public InitiateMeterTestOrDisplay1Token generate() throws Exception {
        InitiateMeterTestDisplayTokenClass tokenClass = new InitiateMeterTestDisplayTokenClass();
        InitiateMeterTestOrDisplay1Token token = new InitiateMeterTestOrDisplay1Token(requestID,
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
