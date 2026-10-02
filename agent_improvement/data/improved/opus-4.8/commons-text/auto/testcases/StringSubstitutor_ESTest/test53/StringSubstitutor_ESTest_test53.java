package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test53 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that changing the variable suffix does not affect the escape
     * character, which stays at its default value of '$'.
     */
    @Test(timeout = 4000)
    public void setVariableSuffixLeavesEscapeCharAtDefault() throws Throwable {
        Map<String, Object> emptyValueMap = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyValueMap);

        StringSubstitutor sameSubstitutor = substitutor.setVariableSuffix('r');

        assertEquals('$', sameSubstitutor.getEscapeChar());
    }
}
