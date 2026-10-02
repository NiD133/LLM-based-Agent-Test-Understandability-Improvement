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

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        final String textToSearch = "6~h5%B";
        final CharSequence[] requiredWords = new CharSequence[] {
                "6~h5%B",
                "6~h5%B",
                "6~h5%B",
                "6~h5%B"
        };

        final boolean containsEveryRequiredWord = WordUtils.containsAllWords(textToSearch, requiredWords);

        assertTrue(containsEveryRequiredWord);
    }
}
