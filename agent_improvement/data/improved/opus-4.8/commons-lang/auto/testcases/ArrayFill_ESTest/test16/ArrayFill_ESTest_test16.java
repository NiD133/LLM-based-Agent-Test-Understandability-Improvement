package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test16 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that {@link ArrayFill#clear(char[])} resets every element of a
     * char array to the NUL character and returns the same array instance.
     */
    @Test(timeout = 4000)
    public void clearCharArraySetsAllElementsToNul() throws Throwable {
        char[] singleElementArray = new char[1];

        char[] clearedArray = ArrayFill.clear(singleElementArray);

        char[] expectedAllNul = new char[] { CharUtils.NUL };
        assertArrayEquals(expectedAllNul, clearedArray);
    }
}
