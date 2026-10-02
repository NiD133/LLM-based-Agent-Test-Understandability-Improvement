package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test00 extends CharSetUtils_ESTest_scaffolding {

    /**
     * squeeze() only collapses repeated characters that belong to the supplied
     * character set. Here the input "..." consists of '.' characters, but the
     * set does not contain '.', so nothing is squeezed and the original string
     * is returned unchanged.
     */
    @Test(timeout = 4000)
    public void squeezeLeavesStringUnchangedWhenSetExcludesRepeatedChar() throws Throwable {
        String input = "...";
        String[] characterSet = { "Minimum abbreviation width with offset is %d" };

        String result = CharSetUtils.squeeze(input, characterSet);

        assertEquals("...", result);
    }
}
