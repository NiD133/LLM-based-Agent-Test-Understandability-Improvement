package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test07 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that passing a null variable suffix is rejected: setVariableSuffix(String)
     * validates its argument via Apache Commons Lang's Validate and throws
     * IllegalArgumentException ("Variable suffix must not be null!") when the suffix is null.
     */
    @Test(timeout = 4000)
    public void setVariableSuffixWithNullStringThrowsIllegalArgumentException() throws Throwable {
        StrSubstitutor strSubstitutor = new StrSubstitutor();

        try {
            strSubstitutor.setVariableSuffix((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The null check is performed by org.apache.commons.lang3.Validate.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
