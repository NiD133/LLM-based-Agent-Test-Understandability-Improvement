package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test14 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Calling replaceIn with a null TextStringBuilder should be a no-op that
     * reports no substitution occurred (returns false), while leaving the
     * substitutor's default escape character ('$') unchanged.
     */
    @Test(timeout = 4000)
    public void replaceInNullBuilderReturnsFalseAndKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        boolean altered = substitutor.replaceIn((TextStringBuilder) null);

        assertFalse("Substituting into a null builder must not alter anything", altered);
        assertEquals("Default escape character should remain '$'", '$', substitutor.getEscapeChar());
    }
}
