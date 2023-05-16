package com.retarus.fax.base.responses;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

/**
 * @author thiagon
 * <p>
 * Enum for the reason of a failed fax deletion.
 * Missing if deletion was successful, otherwise one of the following reason messages is returned:
 * <p>
 * NOT_FOUND: No report exists for the given job id.<p>
 * INTERNAL_ERROR: Unspecified server-side error.
 */
public enum Reason {
    NOT_FOUND,
    INTERNAL_ERROR,

    /**
     * This is the default value for the enum, in case the server returns an unknown reason.
     */
    @JsonEnumDefaultValue
    UNKNOWN
}
