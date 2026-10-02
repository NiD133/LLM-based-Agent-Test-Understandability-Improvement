package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test04 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#wrap(String, int)} returns the input unchanged
     * when there are no break points (spaces) to wrap on.
     *
     * The input ".*\b" contains no space, so even though the negative wrap length is
     * clamped to 1 (lengths below 1 are treated as 1), no wrapping can occur and the
     * original String is returned as-is.
     */
    @Test(timeout = 4000)
    public void wrapWithoutBreakableSpaceReturnsInputUnchanged() throws Throwable {
        final String textWithoutSpaces = ".*\b";

        final String wrapped = WordUtils.wrap(textWithoutSpaces, -1995);

        assertEquals(".*\b", wrapped);
    }
}
