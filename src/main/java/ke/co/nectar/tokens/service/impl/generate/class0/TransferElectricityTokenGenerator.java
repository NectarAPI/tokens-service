package ke.co.nectar.tokens.service.impl.generate.class0;

import ke.co.nectar.hsm.prism.impl.exceptions.UnsupportedTokenTypeException;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferElectricityCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.prism.class0.TransferElectricityCreditPrismTokenGenerator;

import java.util.Map;
import java.util.Optional;

public class TransferElectricityTokenGenerator extends Class0Generator {

    public TransferElectricityTokenGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<TransferElectricityCreditToken> generateElectricityToken(
            Map<String, String> params) throws Exception {
        if (getTokenType() == TokenType.NATIVE) {
            TransferElectricityCreditTokenGenerator generator
                    = new TransferElectricityCreditTokenGenerator(getRequestID(),
                                                                    getTokenIdentifier(params),
                                                                    getRandomNo(params),
                                                                    getAmount(params),
                                                                    getKeyExpiryNumber(params),
                                                                    generateDecoderKey(params),
                                                                    getEncryptionAlgorithm(params));
            return Optional.of(generator.generate());

        } else if (getTokenType() == TokenType.PRISM_THRIFT) {
            TransferElectricityCreditPrismTokenGenerator
                    generator = new TransferElectricityCreditPrismTokenGenerator(getRequestID(),
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
