package ke.co.nectar.tokens.service.impl.generate.class1;

import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay2Token;

import java.util.Map;
import java.util.Optional;

public class InitiateMeterTestOrDisplay2TokenGenerator extends Class1Generator {

    public InitiateMeterTestOrDisplay2TokenGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<InitiateMeterTestOrDisplay2Token> generateInitiateMeterTestOrDisplay2Token(
            Map<String, String> params) throws Exception {
        BitString fourDigitManufactureCodeBitString = new BitString(0);
        fourDigitManufactureCodeBitString.setLength(16);
        ManufacturerCode fourDigitManufacturerCode = new ManufacturerCode(fourDigitManufactureCodeBitString);

        ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1.InitiateMeterTestOrDisplay2TokenGenerator generator
                = new ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1
                        .InitiateMeterTestOrDisplay2TokenGenerator(getRequestID(),
                                                                    getControl(params),
                                                                    fourDigitManufacturerCode);
        return Optional.of(generator.generate());
    }

}
