package ke.co.nectar.token.generators.tokensdecoder.class2;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class2.Set3rdSectionDecoderKeyToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class Set3rdSectionDecoderKeyTokenDecoder extends TokenDecoder {

    @Override
    public Set3rdSectionDecoderKeyToken decode(String requestID,
                                               BitString _64bitStringDecryptedDataBlock,
                                               BitString _64bitStringEncryptedDataBlock) throws Exception {
        Set3rdSectionDecoderKeyToken token = new Set3rdSectionDecoderKeyToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
