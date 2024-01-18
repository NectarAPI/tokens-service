package ke.co.nectar.token.generators.tokensgenerator.prism.class0;

import ke.co.nectar.hsm.prism.impl.PrismClientFacade;
import ke.co.nectar.hsm.prism.impl.PrismHSMConnector;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class0.TransferGasCreditToken;

import java.util.Arrays;
import java.util.List;

public class TransferGasCreditPrismTokenGenerator extends Class0TokenGenerator {

    public TransferGasCreditPrismTokenGenerator(String requestID,
                                                String host, int port, String realm,
                                                String username, String password,
                                                IndividualAccountIdentificationNumber
                                                                individualAccountIdentificationNumber,
                                                EncryptionAlgorithm encryptionAlgorithm,
                                                TokenCarrierType tokenCarrierType,
                                                SupplyGroupCode supplyGroupCode,
                                                KeyRevisionNumber keyRevisionNumber,
                                                Amount amountPurchased,
                                                KeyExpiryNumber keyExpiryNumber,
                                                TariffIndex tariffIndex) {
        super(requestID, host, port, realm, username, password, individualAccountIdentificationNumber,
                encryptionAlgorithm, tokenCarrierType, supplyGroupCode,
                keyRevisionNumber, amountPurchased, keyExpiryNumber, tariffIndex);
    }

    @Override
    public List<TransferGasCreditToken> generate() throws Exception {
        PrismHSMConnector connector = new PrismHSMConnector();
        PrismClientFacade prismClientFacade = new PrismClientFacade(getHost(), getPort(), getRealm(),
                                                                    getUsername(), getPassword(), connector);
        return Arrays.asList(prismClientFacade
                .generateTransferGasCreditToken(getRequestID(), individualAccountIdentificationNumber,
                        encryptionAlgorithm, tokenCarrierType, supplyGroupCode, keyRevisionNumber,
                        amountPurchased, keyExpiryNumber, tariffIndex));
    }
}
