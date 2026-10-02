package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test0 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        CharSet lowerCaseAscii = CharSet.ASCII_ALPHA_LOWER;
        String[] emptyDefinitions = new String[2];
        CharSet emptyCharSet = CharSet.getInstance(emptyDefinitions);

        boolean lowerCaseEqualsEmpty = lowerCaseAscii.equals(emptyCharSet);

        assertFalse(emptyCharSet.equals((Object) lowerCaseAscii));
        assertFalse(lowerCaseEqualsEmpty);
    }
}
