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
public class StrSubstitutor_ESTest_test14 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Calling replaceIn on a null StringBuilder should be a no-op that returns
     * false (nothing was substituted), and the substitutor should still expose
     * the default escape character '$'.
     */
    @Test(timeout = 4000)
    public void replaceInNullStringBuilderReturnsFalseAndKeepsDefaultEscapeChar() throws Throwable {
        @SuppressWarnings("unchecked")
        StrLookup<Object> variableResolver =
                (StrLookup<Object>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrSubstitutor substitutor = new StrSubstitutor(variableResolver);

        boolean replaced = substitutor.replaceIn((StringBuilder) null);

        assertFalse("replaceIn(null) should report that nothing was substituted", replaced);
        assertEquals("default escape character should be '$'", '$', substitutor.getEscapeChar());
    }
}
