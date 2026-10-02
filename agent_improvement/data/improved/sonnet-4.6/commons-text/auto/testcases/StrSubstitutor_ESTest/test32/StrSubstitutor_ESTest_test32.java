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
     * Verifies that replacing within a null char array returns null,
     * and that the default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test_replaceNullCharArray_returnsNull_andDefaultEscapeCharIsDollarSign() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        char[] nullSource = null;
        int offset = (int) '$';  // offset = 36 (ASCII value of '$')
        int length = (int) '$';  // length = 36 (ASCII value of '$')

        String result = substitutor.replace(nullSource, offset, length);

        assertNull("Replacing in a null source array should return null", result);
        assertEquals("Default escape character should be '$'", '$', substitutor.getEscapeChar());
    }
}
