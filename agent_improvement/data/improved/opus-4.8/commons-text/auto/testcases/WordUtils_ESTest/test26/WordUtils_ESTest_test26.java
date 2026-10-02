package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test26 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#containsAllWords(CharSequence, CharSequence...)}
     * returns {@code false} when the array of search words is {@code null},
     * regardless of the input text.
     */
    @Test(timeout = 4000)
    public void containsAllWordsReturnsFalseWhenSearchWordsAreNull() throws Throwable {
        boolean containsAllWords = WordUtils.containsAllWords("", (CharSequence[]) null);

        assertFalse(containsAllWords);
    }
}
