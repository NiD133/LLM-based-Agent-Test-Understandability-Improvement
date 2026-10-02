package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test01 extends WordUtils_ESTest_scaffolding {

    /**
     * When the text consists solely of the break character ("|"), wrapping it
     * leaves no actual content, so the result is an empty string. The negative
     * wrap length is clamped to 1 internally and does not affect the outcome.
     */
    @Test(timeout = 4000)
    public void wrapTextMadeOnlyOfBreakCharacterReturnsEmptyString() throws Throwable {
        String textToWrap = "|";
        int negativeWrapLength = -2392;
        String newLineString = "|";
        boolean wrapLongWords = false;
        String breakCharacter = "|";

        String wrapped = WordUtils.wrap(
                textToWrap, negativeWrapLength, newLineString, wrapLongWords, breakCharacter);

        assertEquals("", wrapped);
    }
}
