package ke.co.nectar.token.generators.tokensdecoder.class0;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class0.TransferWaterCreditToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class TransferWaterCreditDecoder extends TokenDecoder {

    public TransferWaterCreditToken decode(String requestID,
                                           BitString _64bitStringDecryptedDataBlock,
                                           BitString _64bitStringEncryptedDataBlock) throws Exception {
        TransferWaterCreditToken token = new TransferWaterCreditToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringDecryptedDataBlock);
        return token;
    }
}
