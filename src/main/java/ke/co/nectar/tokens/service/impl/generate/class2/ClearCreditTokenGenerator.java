package ke.co.nectar.tokens.service.impl.generate.class2;

import ke.co.nectar.hsm.prism.impl.exceptions.UnsupportedTokenTypeException;
import ke.co.nectar.token.domain.token.class2.ClearCreditToken;

import java.util.Map;
import java.util.Optional;

public class ClearCreditTokenGenerator extends Class2Generator {

    public ClearCreditTokenGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<ClearCreditToken> generateClearCreditToken(Map<String, String> params) throws Exception {
        if (getTokenType() == TokenType.NATIVE) {
            ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.ClearCreditTokenGenerator generator
                    = new ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2
                            .ClearCreditTokenGenerator(getRequestID(),
                                                        getRandomNo(params),
                                                        getTokenIdentifier(params),
                                                        getRegister(params),
                                                        generateDecoderKey(params),
                                                        getEncryptionAlgorithm(params));
            return Optional.of(generator.generate());

        } else if (getTokenType() == TokenType.PRISM_THRIFT) {
            ke.co.nectar.token.generators.tokensgenerator.prism.class2.ClearCreditTokenGenerator generator
                    = new ke.co.nectar.token.generators.tokensgenerator.prism.class2
                            .ClearCreditTokenGenerator(getRequestID(),
                                                        getHost(params),
                                                        getPort(params),
                                                        getRealm(params),
                                                        getUsername(params),
                                                        getPassword(params),
                                                        getIndividualAccountIdentificationNumber(params),
                                                        getEncryptionAlgorithm(params),
                                                        getTokenCarrierType(params),
                                                        getSupplyGroupCode(params),
                                                        getKeyRevisionNumber(params),
                                                        getKeyExpiryNumber(params),
                                                        getTariffIndex(params));
            return Optional.of(generator.generate().get(0));

        }
        throw new UnsupportedTokenTypeException();
    }
}
