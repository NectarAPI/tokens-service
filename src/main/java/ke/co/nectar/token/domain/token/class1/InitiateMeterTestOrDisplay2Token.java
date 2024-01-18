package ke.co.nectar.token.domain.token.class1;

import ke.co.nectar.token.domain.Control;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.tokenclass.class1.InitiateMeterTestDisplayTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class1.InitiateMeterTestDisplay2TokenSubClass;
import ke.co.nectar.token.exceptions.*;
import ke.co.nectar.token.generators.tokensdecoder.error.CRCError;

public class InitiateMeterTestOrDisplay2Token extends Class1Token {

    public InitiateMeterTestOrDisplay2Token(String requestID)
            throws InvalidRangeException {
        super(requestID);
        setTokenClass(new InitiateMeterTestDisplayTokenClass());
        setTokenSubClass(new InitiateMeterTestDisplay2TokenSubClass());
    }

    public InitiateMeterTestOrDisplay2Token(String requestID,
                                            InitiateMeterTestDisplayTokenClass tokenClass,
                                            InitiateMeterTestDisplay2TokenSubClass tokenSubClass,
                                            Control control,
                                            ManufacturerCode manufacturerCode) {
        super(requestID, tokenClass, tokenSubClass, control, manufacturerCode);
    }

    @Override
    public String getType() {
        return "InitiateMeterTestOrDisplay1_11";
    }

    public String getBitString() {
        return encryptedTokenBitString;
    }

    public void decode(BitString decryptedTokenBitString,
                       BitString encryptedTokenBitString) throws
            CRCError, InvalidRangeException, InvalidManufacturerCodeException,
            InvalidControlBitStringException, InvalidBitStringException, BitConcatOverflowError {
        if (checkCrc(decryptedTokenBitString, getTokenClass())) {
            setControl(extractControl(decryptedTokenBitString));
            setManufacturerCode(extractManufacturerCode(decryptedTokenBitString));
            setEncryptedTokenBitString(Long.toBinaryString(encryptedTokenBitString.getValue()));
            setDecryptedTokenBitString(Long.toBinaryString(decryptedTokenBitString.getValue()));
        }
    }

    public Control extractControl(BitString _66BitStringAPDU)
            throws InvalidRangeException, InvalidControlBitStringException,
                    InvalidManufacturerCodeException, InvalidBitStringException {
        return new Control(_66BitStringAPDU.extractBits(32, 28),
                extractManufacturerCode(_66BitStringAPDU));
    }

    public ManufacturerCode extractManufacturerCode(BitString _66BitStringAPDU)
            throws InvalidRangeException, InvalidManufacturerCodeException,
                    InvalidBitStringException {
        return new ManufacturerCode(_66BitStringAPDU.extractBits(16, 16));
    }
}
