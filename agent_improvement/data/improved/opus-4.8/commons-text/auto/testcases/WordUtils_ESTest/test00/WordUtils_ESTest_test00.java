package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test00 extends WordUtils_ESTest_scaffolding {

    /**
     * Exercises {@link WordUtils#wrap(String, int, String, boolean, String)} when the same
     * string is reused as the text to wrap, the new-line separator and the wrap-on regex.
     *
     * <p>The wrap length is negative, which the method clamps to 1, so every character matched
     * by the wrap-on pattern triggers a break that inserts the new-line string.</p>
     */
    @Test(timeout = 4000)
    public void wrapWithNegativeLengthAndStringReusedForAllStringArgs() throws Throwable {
        final String text = "|eT ($ElK)2^p:";
        final int negativeWrapLength = -3137;
        final boolean wrapLongWords = false;

        String wrapped = WordUtils.wrap(text, negativeWrapLength, text, wrapLongWords, text);

        assertEquals("eT|eT ($ElK)2^p:$E|eT ($ElK)2^p:)2|eT ($ElK)2^p:p:", wrapped);
    }
}
