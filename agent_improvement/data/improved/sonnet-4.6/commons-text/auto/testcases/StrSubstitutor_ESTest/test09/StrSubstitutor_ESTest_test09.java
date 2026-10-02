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
public class StrSubstitutor_ESTest_test09 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that setting a null variable prefix throws IllegalArgumentException.
     * StrSubstitutor delegates null-checking to Validate.notNull, which throws
     * IllegalArgumentException with the message "Variable prefix must not be null!".
     */
    @Test(timeout = 4000)
    public void test09_setVariablePrefix_nullPrefix_throwsIllegalArgumentException() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        try {
            substitutor.setVariablePrefix((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
