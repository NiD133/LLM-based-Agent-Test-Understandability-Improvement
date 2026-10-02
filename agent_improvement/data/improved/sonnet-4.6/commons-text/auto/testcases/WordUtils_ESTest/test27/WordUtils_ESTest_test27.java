package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test27 extends WordUtils_ESTest_scaffolding {

    // capitalize() should leave a string unchanged when the first word starts with a non-letter
    // and the subsequent word is already capitalized.
    @Test(timeout = 4000)
    public void test_capitalize_stringStartingWithNonLetterAndAlreadyCapitalizedWord_returnsUnchanged() throws Throwable {
        String input = "? AnDuL6yPz+";
        String result = WordUtils.capitalize(input);
        assertEquals("? AnDuL6yPz+", result);
    }
}
