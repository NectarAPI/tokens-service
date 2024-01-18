package ke.co.nectar.token.generators.tokensdecoder.class1;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.Token;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay1Token;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class InitiateMeterTestOrDisplay1TokenDecoder extends TokenDecoder  {

    @Override
    public Token decode(String requestID,
                        BitString _64bitStringDecryptedDataBlock,
                        BitString _64bitStringEncryptedDataBlock) throws Exception {
        InitiateMeterTestOrDisplay1Token token = new InitiateMeterTestOrDisplay1Token(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
