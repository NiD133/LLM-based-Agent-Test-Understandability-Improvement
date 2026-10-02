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

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        // Array of 4 null CharSequence elements — containsAllWords should return false
        // because null words are treated as blank/missing search terms
        CharSequence[] wordsWithNullElements = new CharSequence[4];
        boolean result = WordUtils.containsAllWords("The Validated Object Is Null", wordsWithNullElements);
        assertFalse(result);
    }
}
