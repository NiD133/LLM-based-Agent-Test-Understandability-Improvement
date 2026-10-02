package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test09 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_setVariablePrefix_null_throwsIllegalArgumentException() throws Throwable {
        StringSubstitutor stringSubstitutor0 = new StringSubstitutor();
        // Undeclared exception!
        try {
            stringSubstitutor0.setVariablePrefix((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Variable prefix must not be null!
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
