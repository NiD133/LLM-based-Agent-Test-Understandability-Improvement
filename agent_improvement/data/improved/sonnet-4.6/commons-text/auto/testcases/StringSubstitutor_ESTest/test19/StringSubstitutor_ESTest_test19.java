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
public class StringSubstitutor_ESTest_test19 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn with a null StringBuffer returns false,
     * and that the default escape character remains '$'.
     */
    @Test(timeout = 4000)
    public void test_replaceInNullStringBuffer_returnsFalseAndDefaultEscapeCharIsPreserved() throws Throwable {
        Map<String, Object> emptyVariableMap = new HashMap<>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyVariableMap);

        // Calling replaceIn on a null StringBuffer should return false (nothing to replace)
        boolean replaced = substitutor.replaceIn((StringBuffer) null, 10, 36);

        assertFalse("replaceIn on null StringBuffer should return false", replaced);
        assertEquals("Default escape character should be '$'", '$', substitutor.getEscapeChar());
    }
}
