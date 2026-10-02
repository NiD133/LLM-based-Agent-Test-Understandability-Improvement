package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test23 extends WordUtils_ESTest_scaffolding {

    /**
     * When every search word equals the whole input string, the input
     * trivially contains all of them, so containsAllWords returns true.
     */
    @Test(timeout = 4000)
    public void containsAllWords_whenEverySearchWordMatchesInput_returnsTrue() throws Throwable {
        String input = "6~h5%B";
        CharSequence[] searchWords = {input, input, input, input};

        boolean allWordsFound = WordUtils.containsAllWords(input, searchWords);

        assertTrue(allWordsFound);
    }
}
