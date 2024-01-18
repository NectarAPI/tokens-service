package ke.co.nectar.token.generators.tokensdecoder.class2;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class2.Set4thSectionDecoderKeyToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class Set4thSectionDecoderKeyTokenDecoder extends TokenDecoder {

    @Override
    public Set4thSectionDecoderKeyToken decode(String requestID,
                                               BitString _64bitStringDecryptedDataBlock,
                                               BitString _64bitStringEncryptedDataBlock) throws Exception {
        Set4thSectionDecoderKeyToken token = new Set4thSectionDecoderKeyToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
