package ke.co.nectar.token.domain.token.class0;

import ke.co.nectar.token.domain.Amount;
import ke.co.nectar.token.domain.Crc;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.tokenclass.class0.GasCreditTransferTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class0.GasCreditTransferTokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.exceptions.InvalidTokenException;
import ke.co.nectar.token.miscellaneous.Strings;

import java.util.Date;
import java.util.Optional;

public class TransferGasCreditToken extends Class0Token {

    public TransferGasCreditToken(String requestID)
            throws InvalidRangeException {
        super(requestID);
        setTokenClass(new GasCreditTransferTokenClass());
        setTokenSubClass(new GasCreditTransferTokenSubClass());
    }

    public TransferGasCreditToken(String requestID,
                                  TokenIdentifier tokenIdentifier,
                                  Optional<RandomNo> randomValue,
                                  Amount amountPurchased) throws InvalidRangeException {
        super(requestID,
                new GasCreditTransferTokenClass(),
                new GasCreditTransferTokenSubClass(),
                randomValue,
                tokenIdentifier,
                amountPurchased) ;
    }

    @Override
    public String getType() {
        return "Gas_02";
    }

    public String getBitString() {
        return encryptedTokenBitString;
    }

    public void decode(BitString decryptedTokenBitString,
                       BitString encryptedTokenBitString) throws Exception {
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
