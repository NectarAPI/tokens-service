package ke.co.nectar.token.generators.tokensdecoder;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.Token;

public interface Decoder {
    Token decode(String requestID,
                 BitString _64bitStringDecryptedDataBlock,
                 BitString _64bitStringEncryptedDataBlock)
            throws Exception;
}
