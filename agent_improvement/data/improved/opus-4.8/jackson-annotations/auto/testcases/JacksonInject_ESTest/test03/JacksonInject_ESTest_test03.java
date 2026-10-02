package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test03 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@link JacksonInject.Value#withOptional(Boolean)} produces a
     * distinct Value when given a non-null "optional" flag, so that the new
     * instance is no longer equal to the original EMPTY instance (whose optional
     * flag is null).
     */
    @Test(timeout = 4000)
    public void withOptionalChangesEqualityWithOriginal() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        JacksonInject.Value valueWithOptionalFalse = emptyValue.withOptional(Boolean.FALSE);

        // The two values differ in their "optional" flag (null vs FALSE),
        // so equals must be false in both directions.
        assertFalse(emptyValue.equals(valueWithOptionalFalse));
        assertFalse(valueWithOptionalFalse.equals((Object) emptyValue));
    }
}
