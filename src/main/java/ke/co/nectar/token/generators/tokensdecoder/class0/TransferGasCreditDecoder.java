package ke.co.nectar.token.generators.tokensdecoder.class0;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class0.TransferGasCreditToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class TransferGasCreditDecoder extends TokenDecoder {

    public TransferGasCreditToken decode(String requestID,
                                         BitString _64bitStringDecryptedDataBlock,
                                         BitString _64bitStringEncryptedDataBlock) throws Exception {
        TransferGasCreditToken token = new TransferGasCreditToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
