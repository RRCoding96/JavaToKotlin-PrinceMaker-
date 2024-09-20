package com.makers.princemaker.dto;

import com.makers.princemaker.code.PrinceMakerErrorCode;
import lombok.*;

/**
 * @author Snow
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrinceMakerErrorResponse {
    private PrinceMakerErrorCode errorCode;
    private String errorMessage;
}
