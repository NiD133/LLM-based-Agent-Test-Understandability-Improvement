package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test23 extends WordUtils_ESTest_scaffolding {

    private static final String WORD = "6~h5%B";

    @Test(timeout = 4000)
    public void test23_containsAllWords_returnsTrueWhenAllWordsMatchSource() throws Throwable {
        CharSequence[] searchWords = new CharSequence[] { WORD, WORD, WORD, WORD };

        boolean result = WordUtils.containsAllWords(WORD, searchWords);

        assertTrue(result);
    }
}
