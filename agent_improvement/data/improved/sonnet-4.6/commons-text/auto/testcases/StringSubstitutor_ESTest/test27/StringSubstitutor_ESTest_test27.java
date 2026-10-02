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
public class StringSubstitutor_ESTest_test27 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27_replaceEmptyBufferPreservesEscapeChar() throws Throwable {
        // Build a substitutor with custom prefix, suffix, and escape character 'z'
        HashMap<String, HashMap<Object, Object>> emptyVariableMap = new HashMap<>();
        String variablePrefix = "rp]j";
        String variableSuffix = "wjSX+E%3~eaj_xSbxQ.";
        char escapeChar = 'z';
        StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, HashMap<Object, Object>>) emptyVariableMap,
                variablePrefix, variableSuffix, escapeChar);

        // Replacing a zero-length region of an empty StringBuffer should return a non-null result
        StringBuffer emptyBuffer = new StringBuffer();
        String result = substitutor.replace(emptyBuffer, 0, 0);

        assertEquals(escapeChar, substitutor.getEscapeChar());
        assertNotNull(result);
    }
}
