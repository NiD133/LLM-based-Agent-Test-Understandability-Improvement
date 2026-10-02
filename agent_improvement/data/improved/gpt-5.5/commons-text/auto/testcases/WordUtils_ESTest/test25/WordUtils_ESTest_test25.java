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
    public void test25() throws Throwable {
        final CharSequence text = "(f)>#}c]W/%TXT9},";
        final CharSequence[] searchWords = null;

        final boolean containsAllWords = WordUtils.containsAllWords(text, searchWords);

        assertFalse(containsAllWords);
    }
}
