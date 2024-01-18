package ke.co.nectar.tokens.service.impl.generate.class2;

import ke.co.nectar.hsm.prism.impl.PrismHSMConnector;
import ke.co.nectar.hsm.prism.impl.exceptions.UnsupportedTokenTypeException;
import ke.co.nectar.token.domain.MaximumPowerLimit;
import ke.co.nectar.token.domain.token.class2.SetMaximumPowerLimitToken;
import ke.co.nectar.tokens.service.impl.generate.exceptions.UnsupportedFlagTokenTypeException;
import ke.co.nectar.tokens.service.impl.generate.exceptions.UnsupportedFlagTokenValueException;

import java.util.Map;
import java.util.Optional;

public class SetMaximumPowerLimitTokenGenerator extends Class2Generator {

    public SetMaximumPowerLimitTokenGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<SetMaximumPowerLimitToken> generateSetMaximumPowerLimitToken(Map<String, String> params) throws Exception {
        if (getTokenType() == TokenType.NATIVE) {
            ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.SetMaximumPowerLimitTokenGenerator generator
                    = new ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2
                                .SetMaximumPowerLimitTokenGenerator(getRequestID(),
                                                                    getRandomNo(params),
                                                                    getTokenIdentifier(params),
                                                                    getMaximumPowerLimit(params),
                                                                    generateDecoderKey(params),
                                                                    getEncryptionAlgorithm(params));
            return Optional.of(generator.generate());

        } else if (getTokenType() == TokenType.PRISM_THRIFT) {
            ke.co.nectar.token.generators.tokensgenerator.prism.class2
                    .SetMaximumPowerLimitTokenGenerator generator
                        = new ke.co.nectar.token.generators.tokensgenerator.prism.class2
                            .SetMaximumPowerLimitTokenGenerator(getRequestID(),
                                                                getHost(params),
                                                                getPort(params),
                                                                getRealm(params),
                                                                getUsername(params),
                                                                getPassword(params),
                                                                getMaximumPowerLimit(params),
                                                                getFlagTokenType(params),
                                                                getFlagTokenValue(params),
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

    private static MaximumPowerLimit getMaximumPowerLimit(Map<String, String> params)
        throws Exception {
        return new MaximumPowerLimit(Long.parseLong(params.get("maximum_power_limit")));
    }

    private PrismHSMConnector.FlagTokenType getFlagTokenType(Map<String, String> params)
            throws UnsupportedFlagTokenTypeException {
        short flagTokenType = Short.parseShort(params.get("flag_token_type"));
        PrismHSMConnector.FlagTokenType type = PrismHSMConnector.FlagTokenType.getFlagTokenType(flagTokenType);
        if (type != null) {
            return type;
        }
        throw new UnsupportedFlagTokenTypeException(String.format("Unsupported %s flag type", flagTokenType));
    }

    private PrismHSMConnector.FlagTokenValue getFlagTokenValue(Map<String, String> params)
            throws UnsupportedFlagTokenValueException {
        short flagTokenValue = Short.parseShort(params.get("flag_token_value"));
        PrismHSMConnector.FlagTokenValue value = PrismHSMConnector.FlagTokenValue.getFlagTokenValue(flagTokenValue);
        if (value != null) {
            return value;
        }
        throw new UnsupportedFlagTokenValueException(String.format("Unsupported %s flag value", flagTokenValue));
    }
}
