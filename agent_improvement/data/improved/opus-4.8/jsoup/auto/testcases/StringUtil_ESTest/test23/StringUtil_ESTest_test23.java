package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test23 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtil#isInvisibleChar(int)} recognises the
     * soft hyphen (code point 173) as an invisible character.
     */
    @Test(timeout = 4000)
    public void isInvisibleChar_returnsTrueForSoftHyphen() throws Throwable {
        int softHyphenCodePoint = 173;

        boolean isInvisible = StringUtil.isInvisibleChar(softHyphenCodePoint);

        assertTrue("Soft hyphen (code point 173) should be treated as invisible", isInvisible);
    }
}
