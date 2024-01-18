package ke.co.nectar.tokens.service.impl.generate.class1;

import ke.co.nectar.hsm.prism.impl.exceptions.UnsupportedTokenTypeException;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay1Token;


import java.util.Map;
import java.util.Optional;

public class InitiateMeterTestOrDisplay1TokenGenerator extends Class1Generator {

    public InitiateMeterTestOrDisplay1TokenGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<InitiateMeterTestOrDisplay1Token>  generateInitiateMeterTestOrDisplay1Token(
            Map<String, String> params) throws Exception {
        if (getTokenType() == TokenType.NATIVE) {
            BitString twoDigitManufactureCodeBitString = new BitString(0);
            twoDigitManufactureCodeBitString.setLength(8);
            ManufacturerCode twoDigitManufacturerCode = new ManufacturerCode(twoDigitManufactureCodeBitString);

            ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1
                    .InitiateMeterTestOrDisplay1TokenGenerator generator
                    = new ke.co.nectar.token.generators.tokensgenerator.nativetoken.
                            class1.InitiateMeterTestOrDisplay1TokenGenerator(getRequestID(),
                                                                            getControl(params),
                                                                            twoDigitManufacturerCode);
            return Optional.of(generator.generate());

        } else if (getTokenType() == TokenType.PRISM_THRIFT) {
            ke.co.nectar.token.generators.tokensgenerator.prism.class1
                    .InitiateMeterTestOrDisplay1TokenGenerator generator
                    = new ke.co.nectar.token.generators.tokensgenerator.prism.class1
                            .InitiateMeterTestOrDisplay1TokenGenerator(getRequestID(),
                                                                        getHost(params),
                                                                        getPort(params),
                                                                        getRealm(params),
                                                                        getUsername(params),
                                                                        getPassword(params),
                                                                        getIndividualAccountIdentificationNumber(params),
                                                                        getControl(params),
                                                                        getManufacturerCode(params));
            return Optional.of(generator.generate().get(0));

        }
        throw new UnsupportedTokenTypeException();
    }
}
