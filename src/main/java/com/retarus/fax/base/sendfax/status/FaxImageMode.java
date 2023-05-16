package com.retarus.fax.base.sendfax.status;

/**
 * @author thiagon
 * <p>
 * Enum for the fax image mode, which specifies when the fax image will be sent to the recipient.
 * <p>
 * Possible values are:<p>
 * NEVER: the fax image will never be sent<p>
 * SUCCESS_ONLY: the fax image will be sent only if the fax was successful<p>
 * FAILURE_ONLY: the fax image will be sent only if the fax failed<p>
 * ALWAYS: the fax image will be sent regardless of the fax status
 */
public enum FaxImageMode {
    NEVER, SUCCESS_ONLY, FAILURE_ONLY, ALWAYS
}