package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test40 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test40() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        // A single-element char array initialized to the null character '\0'
        char[] singleNullChar = new char[1];
        String result = substitutor.replace(singleNullChar);

        assertNotNull(result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
