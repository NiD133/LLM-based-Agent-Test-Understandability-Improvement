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

    /**
     * Setting a null variable prefix must be rejected: {@link StringSubstitutor#setVariablePrefix(String)}
     * validates the argument and throws an {@link IllegalArgumentException} (raised by
     * {@code org.apache.commons.lang3.Validate}) with the message "Variable prefix must not be null!".
     */
    @Test(timeout = 4000)
    public void setVariablePrefixWithNullStringThrowsIllegalArgumentException() throws Throwable {
        StringSubstitutor stringSubstitutor = new StringSubstitutor();

        try {
            stringSubstitutor.setVariablePrefix((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Variable prefix must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
