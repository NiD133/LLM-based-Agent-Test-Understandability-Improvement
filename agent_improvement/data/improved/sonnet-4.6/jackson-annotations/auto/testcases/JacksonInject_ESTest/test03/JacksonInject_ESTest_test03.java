package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test03 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that withOptional(false) produces a new Value that is not equal
     * to EMPTY (which has optional=null), and that this inequality holds in both
     * directions (equals is symmetric).
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        // Create a derived value with optional=false; EMPTY has optional=null, so they must differ
        JacksonInject.Value valueWithOptionalFalse = emptyValue.withOptional(Boolean.FALSE);

        boolean emptyEqualsOptionalFalse = emptyValue.equals(valueWithOptionalFalse);

        // Symmetry: if emptyValue != valueWithOptionalFalse, then the reverse must also be unequal
        assertFalse(valueWithOptionalFalse.equals((Object) emptyValue));
        assertFalse(emptyEqualsOptionalFalse);
    }
}
