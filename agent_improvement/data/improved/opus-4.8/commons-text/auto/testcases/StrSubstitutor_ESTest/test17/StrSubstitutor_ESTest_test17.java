package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test17 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * replaceIn((StringBuffer) null) should report "not altered" by returning
     * false, and the substitutor must keep the escape character it was built with.
     */
    @Test(timeout = 4000)
    public void replaceInNullStringBufferReturnsFalseAndKeepsEscapeChar() throws Throwable {
        StrLookup<Object> variableResolver = (StrLookup<Object>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        char escapeChar = '`';
        StrSubstitutor substitutor =
                new StrSubstitutor(variableResolver, "prefix", "suffix", escapeChar);

        boolean altered = substitutor.replaceIn((StringBuffer) null);

        assertFalse(altered);
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
