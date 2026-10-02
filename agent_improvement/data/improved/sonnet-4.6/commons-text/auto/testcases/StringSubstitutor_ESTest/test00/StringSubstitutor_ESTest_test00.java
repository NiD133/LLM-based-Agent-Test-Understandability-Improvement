package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test00 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that a default StringSubstitutor uses '$' as the escape character
     * after performing a replace operation on a char array segment.
     */
    @Test(timeout = 4000)
    public void test00_defaultEscapeCharIsDollarSign() throws Throwable {
        // Create a substitutor with default settings (no variable map)
        StringSubstitutor substitutor = new StringSubstitutor();

        // Create a char array of 9 null characters and replace a 1-character segment starting at index 1
        char[] source = new char[9];
        substitutor.replace(source, 1, 1);

        // The default escape character should be '$'
        assertEquals('$', substitutor.getEscapeChar());
    }
}
