package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test20 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Deriving a new Value by setting "writeTypeIdForDefaultImpl" to FALSE on the
     * EMPTY value must produce a distinct, non-equal Value (since EMPTY leaves that
     * flag null), while leaving unrelated state such as id visibility untouched.
     */
    @Test(timeout = 4000)
    public void withWriteTypeIdForDefaultImpl_changesFlag_yieldsUnequalValue() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value withFlagDisabled =
                emptyValue.withWriteTypeIdForDefaultImpl(Boolean.FALSE);

        // Changing the write-type-id flag does not affect id visibility, which stays false.
        assertFalse(withFlagDisabled.getIdVisible());

        // The derived Value differs from EMPTY, so equality must fail in both directions.
        assertFalse(withFlagDisabled.equals(emptyValue));
        assertFalse(emptyValue.equals(withFlagDisabled));
    }
}
