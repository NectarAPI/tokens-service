package ke.co.nectar.token.generators.tokensdecoder.class2;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.Token;
import ke.co.nectar.token.domain.token.class2.ClearTamperConditionToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class ClearTamperConditionTokenDecoder extends TokenDecoder {

    @Override
    public Token decode(String requestID,
                        BitString _64bitStringDecryptedDataBlock,
                        BitString _64bitStringEncryptedDataBlock) throws Exception {
        ClearTamperConditionToken token = new ClearTamperConditionToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
