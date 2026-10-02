package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test22 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22_containsAllWords_returnsFalse_whenWordsArrayContainsNullElements() throws Throwable {
        // Create a 4-element array but only populate the first slot;
        // the remaining three slots default to null.
        CharSequence[] wordsToSearch = new CharSequence[4];
        wordsToSearch[0] = (CharSequence) "The validated object is null";

        // containsAllWords must return false because the array has null elements (indices 1-3).
        boolean result = WordUtils.containsAllWords("The Validated Object Is Null", wordsToSearch);
        assertFalse(result);
    }
}
