package ke.co.nectar.token.generators.tokensgenerator.prism.class2;

import ke.co.nectar.hsm.prism.impl.PrismClientFacade;
import ke.co.nectar.hsm.prism.impl.PrismHSMConnector;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class2.SetMaximumPowerLimitToken;

import java.util.Arrays;
import java.util.List;

public class SetMaximumPowerLimitTokenGenerator extends Class2TokenGenerator<SetMaximumPowerLimitToken> {

    private MaximumPowerLimit maximumPowerLimit;
    private PrismHSMConnector.FlagTokenType flagTokenType;
    private PrismHSMConnector.FlagTokenValue flagTokenValue;

    public SetMaximumPowerLimitTokenGenerator(String requestID,
                                              String host, int port, String realm,
                                              String username, String password,
                                              MaximumPowerLimit maximumPowerLimit,
                                              PrismHSMConnector.FlagTokenType flagTokenType,
                                              PrismHSMConnector.FlagTokenValue flagTokenValue,
                                              IndividualAccountIdentificationNumber iain,
                                              EncryptionAlgorithm encryptionAlgorithm,
                                              TokenCarrierType tokenCarrierType,
                                              SupplyGroupCode supplyGroupCode,
                                              KeyRevisionNumber keyRevisionNumber,
                                              KeyExpiryNumber keyExpiryNumber,
                                              TariffIndex tariffIndex) {
        super(requestID, host, port, realm, username, password,
                iain, encryptionAlgorithm, tokenCarrierType, supplyGroupCode,
                keyRevisionNumber, keyExpiryNumber, tariffIndex);
        this.maximumPowerLimit = maximumPowerLimit;
        this.flagTokenType = flagTokenType;
        this.flagTokenValue = flagTokenValue;
    }

    public List<SetMaximumPowerLimitToken> generate() throws Exception {
        PrismHSMConnector connector = new PrismHSMConnector();
        PrismClientFacade facade = new PrismClientFacade(getHost(), getPort(), getRealm(), getUsername(), getPassword(), connector);
        return Arrays.asList(facade.generateSetMaximumPowerLimitToken(getRequestID(), maximumPowerLimit, flagTokenType,
                                                        flagTokenValue, iain, encryptionAlgorithm,
                                                        tokenCarrierType, supplyGroupCode, keyRevisionNumber,
                                                        keyExpiryNumber,  tariffIndex));
    }
}
