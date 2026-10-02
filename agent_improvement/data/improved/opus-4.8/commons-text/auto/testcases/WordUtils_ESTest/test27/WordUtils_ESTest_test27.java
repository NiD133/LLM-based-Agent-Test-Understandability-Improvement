package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test27 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#capitalize(String)} leaves a String unchanged
     * when the first character of every whitespace-separated word is already
     * capitalized. Here both words ("?" and "AnDuL6yPz+") already begin with a
     * non-lowercase character, so capitalization is a no-op.
     */
    @Test(timeout = 4000)
    public void capitalizeLeavesAlreadyCapitalizedWordsUnchanged() throws Throwable {
        String alreadyCapitalized = "? AnDuL6yPz+";

        String result = WordUtils.capitalize(alreadyCapitalized);

        assertEquals("? AnDuL6yPz+", result);
    }
}
