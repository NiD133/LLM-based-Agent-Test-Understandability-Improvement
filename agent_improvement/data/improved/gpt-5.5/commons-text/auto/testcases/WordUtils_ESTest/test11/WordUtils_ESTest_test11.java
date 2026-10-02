package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test11 extends WordUtils_ESTest_scaffolding {

    private static final String EMPTY_TEXT = "";

    @Test(timeout = 4000)
    public void swapCaseReturnsEmptyStringWhenInputIsEmpty() throws Throwable {
        String swappedText = WordUtils.swapCase(EMPTY_TEXT);

        assertEquals(EMPTY_TEXT, swappedText);
    }
}
