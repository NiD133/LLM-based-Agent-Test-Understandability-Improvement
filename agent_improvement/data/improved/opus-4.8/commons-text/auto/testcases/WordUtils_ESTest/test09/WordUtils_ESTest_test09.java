package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test09 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#swapCase(String)} inverts the case of every
     * letter. Since the input is entirely lower case, the result is its upper case
     * counterpart (lower-case letters that start a word become title case, which for
     * ASCII letters is the same as upper case).
     */
    @Test(timeout = 4000)
    public void swapCaseOfAllLowerCaseStringReturnsUpperCase() throws Throwable {
        String swapped = WordUtils.swapCase("upper value is less than lower value");

        assertEquals("UPPER VALUE IS LESS THAN LOWER VALUE", swapped);
    }
}
