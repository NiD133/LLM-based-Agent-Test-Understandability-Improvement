package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test21 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn returns false when the buffer contains only an
     * incomplete variable prefix ("${") with no matching suffix or variable name,
     * and confirms the default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test_replaceIn_withIncompleteVariablePrefix_returnsFalseAndDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Build a buffer that starts with "${" but has no closing "}" — an incomplete variable reference
        StringBuffer incompleteVariable = new StringBuffer(StringSubstitutor.DEFAULT_VAR_START);

        boolean wasModified = interpolator.replaceIn(incompleteVariable);

        assertFalse("replaceIn should return false when no complete variable is found", wasModified);
        assertEquals("Default escape character should be '$'", '$', interpolator.getEscapeChar());
    }
}
