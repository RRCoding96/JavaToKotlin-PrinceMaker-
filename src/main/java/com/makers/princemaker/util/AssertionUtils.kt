package com.makers.princemaker.util

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.exception.PrinceMakerException

fun Boolean.shouldNotTrue(princeMakerErrorCode: PrinceMakerErrorCode) {
    if(this) {
        throw PrinceMakerException(princeMakerErrorCode)
    }
}