package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test29 extends JsonFormat_ESTest_scaffolding {

    /**
     * Calling withLenient(null) on a Value whose leniency is already unset
     * is a no-op: it returns the very same instance, leaving radix and shape
     * untouched.
     */
    @Test(timeout = 4000)
    public void withLenientNullKeepsSameInstanceAndRadix() throws Throwable {
        JsonFormat.Value valueWithRadix = JsonFormat.Value.forRadix(1);

        JsonFormat.Value afterUnsetLenient = valueWithRadix.withLenient((Boolean) null);

        assertSame("withLenient(null) on an already-unset value should return the same instance",
                valueWithRadix, afterUnsetLenient);
        assertEquals("radix should be preserved", 1, afterUnsetLenient.getRadix());
        assertFalse("forRadix leaves shape as ANY, so no explicit shape is set",
                afterUnsetLenient.hasShape());
    }
}
