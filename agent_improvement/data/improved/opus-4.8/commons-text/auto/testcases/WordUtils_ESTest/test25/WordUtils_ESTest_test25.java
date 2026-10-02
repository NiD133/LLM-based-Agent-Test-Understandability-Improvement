package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test25 extends WordUtils_ESTest_scaffolding {

    /**
     * containsAllWords should return false when the array of search words is null,
     * regardless of the text being searched.
     */
    @Test(timeout = 4000)
    public void containsAllWordsReturnsFalseWhenSearchWordsAreNull() throws Throwable {
        String text = "(f)>#}c]W/%TXT9},";
        CharSequence[] nullSearchWords = null;

        boolean result = WordUtils.containsAllWords(text, nullSearchWords);

        assertFalse(result);
    }
}
