package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test27 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that calling withIgnoreUnknown() on a Value that already has ignoreUnknown=true
     * is a no-op: the method must return the exact same instance rather than creating a new one,
     * and all other properties (allowGetters, allowSetters, merge) must remain unchanged.
     */
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        // Construct a Value with ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false.
        // No specific property names are ignored (null set).
        JsonIgnoreProperties.Value valueWithIgnoreUnknownAlreadyTrue =
                JsonIgnoreProperties.Value.construct(
                        (Set<String>) null,  // no specific properties to ignore
                        true,                // ignoreUnknown
                        true,                // allowGetters
                        false,               // allowSetters
                        false                // merge
                );

        // withIgnoreUnknown() should be idempotent: since ignoreUnknown is already true,
        // the method returns 'this' without allocating a new object.
        JsonIgnoreProperties.Value result = valueWithIgnoreUnknownAlreadyTrue.withIgnoreUnknown();

        assertSame("withIgnoreUnknown() must return the same instance when ignoreUnknown is already true",
                valueWithIgnoreUnknownAlreadyTrue, result);

        // Confirm that the other properties were not altered by the no-op call.
        assertTrue("allowGetters should still be true", result.getAllowGetters());
        assertFalse("allowSetters should still be false", result.getAllowSetters());
        assertFalse("merge should still be false", result.getMerge());
    }
}
