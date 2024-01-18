package ke.co.nectar.token.generators.tokensdecoder.class0;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import ke.co.nectar.token.exceptions.*;
import ke.co.nectar.token.generators.tokensdecoder.TokenDecoder;
import ke.co.nectar.token.generators.tokensdecoder.error.*;

public class TransferElectricityCreditDecoder extends TokenDecoder {

    public TransferElectricityCreditToken decode(String requestID,
                                                 BitString _64bitStringDecryptedDataBlock,
                                                 BitString _64bitStringEncryptedDataBlock)
            throws CRCError, OldError, UsedError, KeyExpiredError, DDTKError, InvalidRangeException,
                    OverflowError, KeyTypeError, FormatError, RangeError, FunctionError,
                    InvalidTokenException, InvalidUnitsPurchasedBitsException, InvalidUnitsPurchasedException,
                    InvalidDateTimeBitsException, InvalidBitStringException, BitConcatOverflowError {
        TransferElectricityCreditToken token = new TransferElectricityCreditToken(requestID);
        token.decode(_64bitStringDecryptedDataBlock, _64bitStringEncryptedDataBlock);
        return token;
    }
}
