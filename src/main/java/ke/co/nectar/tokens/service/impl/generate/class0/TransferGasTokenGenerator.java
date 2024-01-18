package ke.co.nectar.tokens.service.impl.generate.class0;

import ke.co.nectar.hsm.prism.impl.exceptions.UnsupportedTokenTypeException;
import ke.co.nectar.token.domain.token.class0.TransferGasCreditToken;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferGasCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.prism.class0.TransferGasCreditPrismTokenGenerator;

import java.util.Map;
import java.util.Optional;

public class TransferGasTokenGenerator extends Class0Generator {

    public TransferGasTokenGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<TransferGasCreditToken> generateGasToken(
            Map<String, String> params) throws Exception {
        if (getTokenType() == TokenType.NATIVE) {
            TransferGasCreditTokenGenerator generator
                    = new TransferGasCreditTokenGenerator(getRequestID(),
                                                            getTokenIdentifier(params),
                                                            getRandomNo(params),
                                                            getAmount(params),
                                                            getKeyExpiryNumber(params),
                                                            generateDecoderKey(params),
                                                            getEncryptionAlgorithm(params));
            return Optional.of(generator.generate());

        } else if (getTokenType() == TokenType.PRISM_THRIFT) {
            TransferGasCreditPrismTokenGenerator generator
                    = new TransferGasCreditPrismTokenGenerator(getRequestID(),
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
                                                                getAmount(params),
                                                                getKeyExpiryNumber(params),
                                                                getTariffIndex(params));
            return Optional.of(generator.generate().get(0));

        }
        throw new UnsupportedTokenTypeException();
    }
}
