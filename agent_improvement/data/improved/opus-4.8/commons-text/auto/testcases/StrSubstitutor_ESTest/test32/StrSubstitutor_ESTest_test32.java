package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test32 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Replacing over a null char[] template should return null, and the
     * substitutor created via the no-arg constructor should keep the default
     * escape character ('$').
     */
    @Test(timeout = 4000)
    public void replaceNullCharArrayReturnsNullAndKeepsDefaultEscape() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        // offset and length are passed as the int value of '$', but they are
        // never used because a null template short-circuits to a null result.
        String result = substitutor.replace((char[]) null, (int) '$', (int) '$');

        assertNull(result);
        assertEquals(StrSubstitutor.DEFAULT_ESCAPE, substitutor.getEscapeChar());
    }
}
