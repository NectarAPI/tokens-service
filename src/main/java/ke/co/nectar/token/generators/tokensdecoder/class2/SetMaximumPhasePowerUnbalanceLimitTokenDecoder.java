package ke.co.nectar.token.generators.tokensdecoder.class2;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class2.SetMaximumPhasePowerUnbalanceLimitToken;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;

public class SetMaximumPhasePowerUnbalanceLimitTokenDecoder extends TokenDecoder {

    @Override
    public SetMaximumPhasePowerUnbalanceLimitToken decode(String requestID,
                                                          BitString _64bitStringDecryptedDataBlock,
                                                          BitString _64bitStringEncryptedDataBlock) throws Exception {
        SetMaximumPhasePowerUnbalanceLimitToken token = new SetMaximumPhasePowerUnbalanceLimitToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
