package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test07 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that StringSubstitutor.replace(Object, Map, String, String) throws
     * IllegalArgumentException when the variable suffix argument is null.
     *
     * The method requires a non-null suffix to delimit variable expressions (e.g., "}"),
     * so passing null should be rejected immediately with a descriptive error message.
     */
    @Test(timeout = 4000)
    public void test07_replaceWithNullSuffix_throwsIllegalArgumentException() throws Throwable {
        // A source object that looks like the start of a variable expression
        Object sourceWithVariablePrefix = "${";

        // An empty variable map — no substitutions will be performed
        Map<String, Object> emptyVariableMap = new HashMap<String, Object>();

        // A valid variable prefix, but no corresponding suffix (null)
        String variablePrefix = "${";
        String variableSuffix = null;

        try {
            StringSubstitutor.replace(sourceWithVariablePrefix, emptyVariableMap, variablePrefix, variableSuffix);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: "Variable suffix must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
