package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test46 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Changing the value delimiter must not affect the escape character.
     * A freshly created StringSubstitutor uses '$' as its default escape
     * character, so after setting the value delimiter to '$' the escape
     * character is still expected to be '$'.
     */
    @Test(timeout = 4000)
    public void settingValueDelimiterLeavesEscapeCharUnchanged() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        StringSubstitutor sameSubstitutor = substitutor.setValueDelimiter('$');

        char expectedDefaultEscapeChar = '$';
        assertEquals(expectedDefaultEscapeChar, sameSubstitutor.getEscapeChar());
    }
}
