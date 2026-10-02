package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test4 extends CharSet_ESTest_scaffolding {

    private static final String GENERATED_CHAR_SET_PATTERN = "\"@mi/\u007FvnsJ<U6tm^D-O";

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        String[] generatedPatterns = new String[6];
        generatedPatterns[2] = GENERATED_CHAR_SET_PATTERN;

        CharSet charSet = CharSet.getInstance(generatedPatterns);

        assertNotNull(charSet);
    }
}
