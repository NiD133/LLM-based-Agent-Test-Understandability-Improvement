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
     * Verifies that replaceIn returns false when the source buffer is null,
     * regardless of the offset and length arguments, and that the substitutor
     * keeps its default escape character ('$').
     */
    @Test(timeout = 4000)
    public void replaceInNullBufferReturnsFalse() throws Throwable {
        Map<String, Object> emptyValues = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyValues);

        StringBuffer nullSource = null;
        int offset = 10;
        int length = 36;
        boolean altered = substitutor.replaceIn(nullSource, offset, length);

        assertFalse("Substituting in a null buffer must report no change", altered);
        assertEquals("Escape char should remain the default '$'",
                '$', substitutor.getEscapeChar());
    }
}
