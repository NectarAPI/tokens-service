package ke.co.nectar.token.domain.token.class0;

import ke.co.nectar.token.domain.Amount;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.tokenclass.class0.ElectricityCreditTransferTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class0.ElectricityCreditTransferTokenSubClass;
import ke.co.nectar.token.exceptions.*;
import ke.co.nectar.token.generators.tokensdecoder.error.*;
import ke.co.nectar.token.miscellaneous.Strings;

import java.util.Optional;

public class TransferElectricityCreditToken extends Class0Token {

    public TransferElectricityCreditToken(String requestID)
            throws InvalidRangeException {
        super(requestID);
        setTokenClass(new ElectricityCreditTransferTokenClass());
        setTokenSubClass(new ElectricityCreditTransferTokenSubClass());
    }

    public TransferElectricityCreditToken(String requestID,
                                          TokenIdentifier tokenIdentifier,
                                          Optional<RandomNo> randomValue,
                                          Amount amountPurchased) throws InvalidRangeException {
        super(requestID,
                new ElectricityCreditTransferTokenClass(),
                new ElectricityCreditTransferTokenSubClass(),
                randomValue,
                tokenIdentifier,
                amountPurchased);
    }

    @Override
    public String getType() {
        return "Electricity_00";
    }

    public String getBitString() {
        return encryptedTokenBitString;
    }

    @Override
    public void decode(BitString decryptedTokenBitString,
                       BitString encryptedTokenBitString)
            throws CRCError, OldError, UsedError, KeyExpiredError, DDTKError,
                    OverflowError, KeyTypeError, FormatError, RangeError, FunctionError,
                    InvalidTokenException, InvalidDateTimeBitsException, InvalidRangeException,
                    InvalidUnitsPurchasedException, InvalidUnitsPurchasedBitsException,
                    InvalidBitStringException, BitConcatOverflowError {
        if (checkCrc(decryptedTokenBitString, getTokenClass())) {
            setRND(extractRandomNo(decryptedTokenBitString));
            setTokenIdentifier(extractTokenIdentifier(decryptedTokenBitString));
            setAmountPurchased(extractAmount(decryptedTokenBitString));
            setCrc(Optional.of(extractCrc(decryptedTokenBitString)));
            setEncryptedTokenBitString(Long.toBinaryString(encryptedTokenBitString.getValue()));
            setDecryptedTokenBitString(Long.toBinaryString(decryptedTokenBitString.getValue()));
        } else
            throw new InvalidTokenException(Strings.INVALID_TOKEN);
    }
}
