package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test18 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#initials(String)} collects the first
     * character of each whitespace-separated word, leaving case unchanged.
     *
     * <p>For the input "upper valuecannot be less than -1" the words are
     * "upper", "valuecannot", "be", "less", "than" and "-1", so the
     * resulting initials are "u", "v", "b", "l", "t" and "-" → "uvblt-".</p>
     */
    @Test(timeout = 4000)
    public void initialsReturnsFirstCharacterOfEachWord() throws Throwable {
        String initials = WordUtils.initials("upper valuecannot be less than -1");

        assertEquals("uvblt-", initials);
    }
}
