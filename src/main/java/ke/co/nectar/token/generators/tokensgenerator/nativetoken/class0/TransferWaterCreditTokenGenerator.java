package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0;

import ke.co.nectar.token.domain.Amount;
import ke.co.nectar.token.domain.KeyExpiryNumber;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.token.class0.TransferWaterCreditToken;
import ke.co.nectar.token.generators.utils.Utils;

import java.util.Optional;

public class TransferWaterCreditTokenGenerator extends Class0TokenGenerator {

    public TransferWaterCreditTokenGenerator(String requestID,
                                             TokenIdentifier tokenIdentifier,
                                             RandomNo randomValue,
                                             Amount amountPurchased,
                                             KeyExpiryNumber keyExpiryNumber,
                                             DecoderKey decoderKey,
                                             EncryptionAlgorithm encryptionAlgorithm) {
        super(requestID, tokenIdentifier, randomValue, amountPurchased, keyExpiryNumber, decoderKey, encryptionAlgorithm);
    }

    public TransferWaterCreditToken generate() throws Exception {
        Utils.validateTokenIdentifier(tokenIdentifier, keyExpiryNumber);
        TransferWaterCreditToken token = new TransferWaterCreditToken(getRequestID(),
                                                                        getTokenIdentifier(),
                                                                        Optional.of(getRandomValue()),
                                                                        getAmountPurchased());
        BitString _64BitDataBlock = generate64BitDataBlock(token);
        BitString _64bitStringEncryptedBitString = encryptionAlgorithm.encrypt(decoderKey, _64BitDataBlock);
        String _66bitStringEncryptedBlockAT = transpose66BitString(token.getTokenClass(), _64bitStringEncryptedBitString);
        token.setEncryptedTokenBitString(_66bitStringEncryptedBlockAT);
        return token;
    }
}
