package ke.co.nectar.token.domain.token.class0;

import ke.co.nectar.token.domain.Amount;
import ke.co.nectar.token.domain.Crc;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.tokenclass.class0.WaterCreditTransferTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class0.WaterCreditTransferTokenSubClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.exceptions.InvalidTokenException;
import ke.co.nectar.token.miscellaneous.Strings;

import java.util.Date;
import java.util.Optional;

public class TransferWaterCreditToken extends Class0Token  {

    public TransferWaterCreditToken(String requestID)
            throws InvalidRangeException {
        super(requestID);
        setTokenClass(new WaterCreditTransferTokenClass());
        setTokenSubClass(new WaterCreditTransferTokenSubClass());
    }

    public TransferWaterCreditToken(String requestID,
                                    TokenIdentifier tokenIdentifier,
                                    Optional<RandomNo> randomValue,
                                    Amount amountPurchased) throws InvalidRangeException {
        super(requestID,
                new WaterCreditTransferTokenClass(),
                new WaterCreditTransferTokenSubClass(),
                randomValue,
                tokenIdentifier,
                amountPurchased ) ;
    }

    @Override
    public String getType() {
        return "Water_01";
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
