package ke.co.nectar.tokens.service.impl.generate.class2;

import ke.co.nectar.hsm.prism.impl.exceptions.UnsupportedTokenTypeException;
import ke.co.nectar.token.domain.Pad;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class2.ClearTamperConditionToken;

import java.util.Map;
import java.util.Optional;

public class ClearTamperConditionTokenGenerator extends Class2Generator {

    public ClearTamperConditionTokenGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<ClearTamperConditionToken> generateClearTamperConditionToken(Map<String, String> params)
            throws Exception {
        if (getTokenType() == TokenType.NATIVE) {
            ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.ClearTamperConditionTokenGenerator generator
                    = new ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2
                            .ClearTamperConditionTokenGenerator(getRequestID(),
                                                                getRandomNo(params),
                                                                getTokenIdentifier(params),
                                                                getPad(params),
                                                                generateDecoderKey(params),
                                                                getEncryptionAlgorithm(params));
            return Optional.of(generator.generate());

        } else if (getTokenType() == TokenType.PRISM_THRIFT) {
            ke.co.nectar.token.generators.tokensgenerator.prism.class2.ClearTamperConditionTokenGenerator generator
                        = new ke.co.nectar.token.generators.tokensgenerator.prism.class2
                                .ClearTamperConditionTokenGenerator(getRequestID(),
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

    private static Pad getPad(Map<String, String> params) throws Exception {
        String extractedPad = params.get("pad");
        BitString padRateBitString = new BitString(Long.parseLong(extractedPad));
        padRateBitString.setLength(16);
        return new Pad(padRateBitString);
    }
}
