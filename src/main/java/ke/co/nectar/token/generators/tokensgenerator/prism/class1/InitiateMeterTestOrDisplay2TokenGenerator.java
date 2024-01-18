package ke.co.nectar.token.generators.tokensgenerator.prism.class1;

import ke.co.nectar.hsm.prism.impl.PrismClientFacade;
import ke.co.nectar.hsm.prism.impl.PrismHSMConnector;
import ke.co.nectar.token.domain.Control;
import ke.co.nectar.token.domain.IndividualAccountIdentificationNumber;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay2Token;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class InitiateMeterTestOrDisplay2TokenGenerator extends Class1TokenGenerator {

    public InitiateMeterTestOrDisplay2TokenGenerator(String requestID, String host, int port, String realm,
                                                     String username, String password,
                                                     IndividualAccountIdentificationNumber iain,
                                                     Control control,
                                                     ManufacturerCode manufacturerCode) {
        super(requestID, host, port, realm, username, password, iain, control, manufacturerCode);
    }

    public List<InitiateMeterTestOrDisplay2Token> generate() throws Exception {
        String messageID = UUID.randomUUID().toString();
        PrismHSMConnector connector = new PrismHSMConnector();
        PrismClientFacade prismClientFacade = new PrismClientFacade(getHost(), getPort(), getRealm(),
                                                                    getUsername(), getPassword(), connector);
        return Arrays.asList(
                prismClientFacade
                        .generateInitiateMeterTestOrDisplay2Token(messageID, iain, control, manufacturerCode));
    }
}
