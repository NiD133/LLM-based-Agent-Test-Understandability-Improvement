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
public class JsonAutoDetect_ESTest_test37 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * The default visibility configuration should allow auto-detection of all
     * non-private single-scalar-argument constructors (NON_PRIVATE threshold).
     */
    @Test(timeout = 4000)
    public void test_defaultVisibility_scalarConstructorVisibilityIsNonPrivate() throws Throwable {
        JsonAutoDetect.Value defaultVisibilityConfig = JsonAutoDetect.Value.defaultVisibility();
        JsonAutoDetect.Visibility scalarConstructorVisibility = defaultVisibilityConfig.getScalarConstructorVisibility();
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, scalarConstructorVisibility);
    }
}
