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

    @Test(timeout = 4000)
    public void test_containsAllWords_returnsFalse_whenWordsArrayIsNull() throws Throwable {
        // containsAllWords must return false when the words vararg is explicitly null
        boolean result = WordUtils.containsAllWords("(f)>#}c]W/%TXT9},", (CharSequence[]) null);
        assertFalse(result);
    }
}
