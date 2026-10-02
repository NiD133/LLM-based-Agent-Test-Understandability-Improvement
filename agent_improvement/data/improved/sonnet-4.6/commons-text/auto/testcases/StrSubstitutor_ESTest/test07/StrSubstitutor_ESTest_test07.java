package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test07 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that setting a null variable suffix throws an IllegalArgumentException,
     * because the suffix is required for identifying variable boundaries in the template string.
     */
    @Test(timeout = 4000)
    public void test_setVariableSuffix_withNull_throwsIllegalArgumentException() throws Throwable {
        StrSubstitutor strSubstitutor = new StrSubstitutor();
        try {
            strSubstitutor.setVariableSuffix((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
