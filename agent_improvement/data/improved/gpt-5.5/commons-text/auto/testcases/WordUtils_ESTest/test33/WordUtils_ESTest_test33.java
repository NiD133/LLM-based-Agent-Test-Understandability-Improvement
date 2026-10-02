package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test33 extends WordUtils_ESTest_scaffolding {

    private static final String WORD_UTILS_CLASS_NAME = "org.apache.commons.text.WordUtils";
    private static final int START_AT_BEGINNING = 0;
    private static final int NO_UPPER_LIMIT = -1;

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        String abbreviated = WordUtils.abbreviate(
                WORD_UTILS_CLASS_NAME,
                START_AT_BEGINNING,
                NO_UPPER_LIMIT,
                WORD_UTILS_CLASS_NAME);

        assertEquals(WORD_UTILS_CLASS_NAME, abbreviated);
    }
}
