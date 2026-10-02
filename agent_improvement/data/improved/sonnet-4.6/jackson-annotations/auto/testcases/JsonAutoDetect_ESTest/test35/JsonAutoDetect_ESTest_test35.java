package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test35 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that calling withScalarConstructorVisibility with the same visibility
     * that DEFAULT already has (NON_PRIVATE) returns the identical DEFAULT instance,
     * confirming the no-op identity optimisation in Value.construct().
     */
    @Test(timeout = 4000)
    public void test35() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;

        // DEFAULT already uses NON_PRIVATE for scalar constructors, so this should be a no-op
        JsonAutoDetect.Value resultVisibility = defaultVisibility.withScalarConstructorVisibility(
                JsonAutoDetect.Visibility.NON_PRIVATE);

        assertSame(resultVisibility, defaultVisibility);
    }
}
