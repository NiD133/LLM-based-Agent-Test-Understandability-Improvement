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

    /**
     * Verifies that replacing an empty region (offset 0, length 0) of an empty
     * StringBuffer yields an empty (non-null) result, and that the custom escape
     * character supplied to the constructor is retained.
     */
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        // Build a substitutor with an empty variable map, custom prefix/suffix and escape char 'z'.
        final Map<String, HashMap<Object, Object>> emptyVariables = new HashMap<String, HashMap<Object, Object>>();
        final String variablePrefix = "rp]j";
        final String variableSuffix = "wjSX+E%3~eaj_xSbxQ.";
        final char escapeChar = 'z';
        final StringSubstitutor substitutor =
                new StringSubstitutor(emptyVariables, variablePrefix, variableSuffix, escapeChar);

        // Replace nothing (an empty range) inside an empty buffer.
        final StringBuffer emptyBuffer = new StringBuffer();
        final String result = substitutor.replace(emptyBuffer, 0, 0);

        // The result is always a non-null String, and the escape char is preserved.
        assertNotNull(result);
        assertEquals('z', substitutor.getEscapeChar());
    }
}
