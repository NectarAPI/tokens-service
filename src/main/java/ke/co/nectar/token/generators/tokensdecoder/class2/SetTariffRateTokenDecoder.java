package ke.co.nectar.token.generators.tokensdecoder.class2;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class2.SetTariffRateToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class SetTariffRateTokenDecoder extends TokenDecoder {

    @Override
    public SetTariffRateToken decode(String requestID,
                                     BitString _64bitStringDecryptedDataBlock,
                                     BitString _64bitStringEncryptedDataBlock) throws Exception {
        SetTariffRateToken token = new SetTariffRateToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
