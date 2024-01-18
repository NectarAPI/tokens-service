package ke.co.nectar.token.generators.tokensdecoder.class2;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class2.Set1stSectionDecoderKeyToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class Set1stSectionDecoderKeyTokenDecoder extends TokenDecoder {

    @Override
    public Set1stSectionDecoderKeyToken decode(String requestID,
                                               BitString _64bitStringDecryptedDataBlock,
                                               BitString _64bitStringEncryptedDataBlock) throws Exception {
        Set1stSectionDecoderKeyToken token = new Set1stSectionDecoderKeyToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
