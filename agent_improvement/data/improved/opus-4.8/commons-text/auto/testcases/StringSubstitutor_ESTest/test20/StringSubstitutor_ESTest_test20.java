package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test20 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * replaceIn((StringBuffer) null) should be a no-op: it returns false because
     * there is nothing to substitute, and the substitutor's default escape
     * character ('$') is left unchanged.
     */
    @Test(timeout = 4000)
    public void replaceInNullStringBufferReturnsFalseAndKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        boolean replaced = substitutor.replaceIn((StringBuffer) null);

        assertFalse("Replacing into a null buffer should report no change", replaced);
        assertEquals("Default escape char should remain '$'", '$', substitutor.getEscapeChar());
    }
}
