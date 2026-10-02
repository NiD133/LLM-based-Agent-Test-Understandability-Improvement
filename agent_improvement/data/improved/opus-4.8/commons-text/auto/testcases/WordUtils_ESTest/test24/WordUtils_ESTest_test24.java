package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test24 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#containsAllWords(CharSequence, CharSequence...)}
     * returns {@code false} when the search-words array contains a {@code null}
     * (blank) element. A blank word can never be matched, so the method short-circuits
     * to {@code false}.
     */
    @Test(timeout = 4000)
    public void containsAllWordsReturnsFalseWhenSearchWordIsNull() throws Throwable {
        String text = "The Validated Object Is Null";
        // An array of four null elements; the first null counts as a blank word.
        CharSequence[] searchWords = new CharSequence[4];

        boolean allWordsFound = WordUtils.containsAllWords(text, searchWords);

        assertFalse(allWordsFound);
    }
}
