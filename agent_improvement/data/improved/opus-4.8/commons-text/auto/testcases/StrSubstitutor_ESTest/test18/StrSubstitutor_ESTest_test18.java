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
public class StrSubstitutor_ESTest_test18 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing on a null StringBuffer source is a no-op that
     * leaves the substitutor's configuration (here, the escape character)
     * untouched.
     */
    @Test(timeout = 4000)
    public void replaceOnNullStringBufferKeepsEscapeChar() throws Throwable {
        Map<String, Object> emptyLookup = new HashMap<String, Object>();
        char escapeChar = ';';
        String variablePrefix = " [stringLookupMap=";
        String variableSuffix = " [stringLookupMap=";

        StrSubstitutor substitutor =
                new StrSubstitutor(emptyLookup, variablePrefix, variableSuffix, escapeChar);

        // A null source returns null and performs no substitution.
        String result = substitutor.replace((StringBuffer) null, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertNull(result);

        assertEquals(';', substitutor.getEscapeChar());
    }
}
