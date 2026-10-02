package org.apache.commons.text;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test09 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Setting a null variable prefix must be rejected: the underlying
     * Validate.isTrue check throws IllegalArgumentException with the message
     * "Variable prefix must not be null!".
     */
    @Test(timeout = 4000)
    public void setVariablePrefixWithNullStringThrowsIllegalArgumentException() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        try {
            substitutor.setVariablePrefix((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The validation is performed by org.apache.commons.lang3.Validate.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
