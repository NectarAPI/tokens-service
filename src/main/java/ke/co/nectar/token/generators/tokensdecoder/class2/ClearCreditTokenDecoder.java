package ke.co.nectar.token.generators.tokensdecoder.class2;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.Token;
import ke.co.nectar.token.domain.token.class2.ClearCreditToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class ClearCreditTokenDecoder extends TokenDecoder {

    @Override
    public Token decode(String requestID,
                        BitString _64bitStringDecryptedDataBlock,
                        BitString _64bitStringEncryptedDataBlock) throws Exception {
        ClearCreditToken token = new ClearCreditToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
