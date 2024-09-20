package com.makers.princemaker.exception;

import com.makers.princemaker.code.PrinceMakerErrorCode;
import lombok.Getter;

/**
 * @author Snow
 */
@Getter
public class PrinceMakerException extends RuntimeException {
    private PrinceMakerErrorCode princeMakerErrorCode;
    private String detailMessage;

    public PrinceMakerException(PrinceMakerErrorCode princeMakerErrorCode) {
        super(princeMakerErrorCode.getMessage());
        this.princeMakerErrorCode = princeMakerErrorCode;
        this.detailMessage = princeMakerErrorCode.getMessage();
    }

    public PrinceMakerException(PrinceMakerErrorCode princeMakerErrorCode,
                                String detailMessage) {
        super(detailMessage);
        this.princeMakerErrorCode = princeMakerErrorCode;
        this.detailMessage = detailMessage;
    }
}
