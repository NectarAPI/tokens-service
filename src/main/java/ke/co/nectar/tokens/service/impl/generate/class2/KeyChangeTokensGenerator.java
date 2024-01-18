package ke.co.nectar.tokens.service.impl.generate.class2;

import ke.co.nectar.hsm.prism.impl.exceptions.UnsupportedTokenTypeException;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class2.Class2Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

;

public class KeyChangeTokensGenerator extends Class2Generator {

    public KeyChangeTokensGenerator(String requestID, TokenType tokenType) {
        super(requestID, tokenType);
    }

    public Optional<List<Class2Token>> generateKeyChangeTokens(Map<String, String> params)
            throws Exception {
        if (getTokenType() == TokenType.NATIVE) {
            ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set1stSectionDecoderKeyTokenGenerator
                        set1stSectionDecoderKeyTokenGenerator
                    = new ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2
                                .Set1stSectionDecoderKeyTokenGenerator(getRequestID(),
                                                                        getKeyExpiryNumberHighOrder(params),
                                                                        getKeyRevisionNo(params),
                                                                        getRolloverKeyChange(params),
                                                                        getKeyType(params),
                                                                        generateDecoderKey(params),
                                                                        generateNewDecoderKey(params),
                                                                        getEncryptionAlgorithm(params));

            ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set2ndSectionDecoderKeyTokenGenerator
                        set2ndSectionDecoderKeyTokenGenerator
                    = new ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2
                            .Set2ndSectionDecoderKeyTokenGenerator(getRequestID(),
                                                                    getKeyExpiryNumberLowOrder(params),
                                                                    getTariffIndex(params),
                                                                    generateDecoderKey(params),
                                                                    generateNewDecoderKey(params),
                                                                    getEncryptionAlgorithm(params));
            List<Class2Token> tokens = new ArrayList<>();
            tokens.add(set1stSectionDecoderKeyTokenGenerator.generate());
            tokens.add(set2ndSectionDecoderKeyTokenGenerator.generate());

            return Optional.of(tokens);

        } else if (getTokenType() == TokenType.PRISM_THRIFT) {
            ke.co.nectar.token.generators.tokensgenerator.prism.class2.KeyChangeTokensGenerator generator
                    = new ke.co.nectar.token.generators.tokensgenerator.prism.class2
                            .KeyChangeTokensGenerator(getRequestID(),
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
                                                        getTariffIndex(params),
                                                        getNewSupplyGroupCode(params),
                                                        getNewKeyRevisionNo(params),
                                                        getNewTariffIndex(params),
                                                        getAllow3Kct(params));
            return Optional.of(generator.generate());

        }
        throw new UnsupportedTokenTypeException();
    }

    private static KeyExpiryNumberHighOrder getKeyExpiryNumberHighOrder (Map<String, String> params)
            throws Exception {
        String kenho = params.get("key_expiry_no");
        BitString kenhoBitString = new BitString(Integer.parseInt(kenho)).extractBits(0, 4);
        return new KeyExpiryNumberHighOrder(kenhoBitString);
    }

    private static KeyExpiryNumberLowOrder getKeyExpiryNumberLowOrder (Map<String, String> params)
            throws Exception {
        String kenho = params.get("key_expiry_no");
        BitString kenhoBitString = new BitString(Integer.parseInt(kenho)).extractBits(4, 4);
        return new KeyExpiryNumberLowOrder(kenhoBitString);
    }

    private static KeyRevisionNumber getKeyRevisionNo(Map<String, String> params) throws Exception {
        String keyRevisionNo = params.get("key_revision_no");
        return new KeyRevisionNumber(Integer.parseInt(keyRevisionNo));
    }

    public static RolloverKeyChange getRolloverKeyChange(Map<String, String> params) throws Exception {
        BitString roBitString = new BitString(Long.parseLong(params.get("ro")));
        roBitString.setLength(1);
        return new RolloverKeyChange(roBitString);
    }

    public static KeyType getKeyType(Map<String, String> params) throws Exception {
        String keyType = params.get("key_type");
        return new KeyType(Integer.parseInt(keyType));
    }
}
