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
public class StrSubstitutor_ESTest_test16 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn on a null StringBuffer is a safe no-op:
     * it returns false (nothing was altered) regardless of the given
     * offset/length, and the substitutor keeps the default escape character.
     */
    @Test(timeout = 4000)
    public void replaceInNullBufferReturnsFalse() throws Throwable {
        Map<String, String> emptyValues = new HashMap<String, String>();
        String variablePrefix = "org.apache.commons.text.lookup.ConstantStringLookup";
        String variableSuffix = "org.apache.commons.text.lookup.ConstantStringLookup";
        StrSubstitutor substitutor = new StrSubstitutor(emptyValues, variablePrefix, variableSuffix);

        StringBuffer nullSource = null;
        boolean altered = substitutor.replaceIn(nullSource, 31, 671);

        assertFalse("replaceIn on a null buffer should report no change", altered);
        assertEquals("escape character should remain the default '$'",
                '$', substitutor.getEscapeChar());
    }
}
