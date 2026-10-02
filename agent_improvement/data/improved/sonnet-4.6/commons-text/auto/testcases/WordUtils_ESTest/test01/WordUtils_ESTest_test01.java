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

    // When the input string consists solely of the wrapOn separator character,
    // the separator is consumed during processing and nothing remains, so the
    // result is an empty string regardless of the (negative) wrapLength.
    @Test(timeout = 4000)
    public void testWrapReturnsEmptyStringWhenInputIsSeparatorAndWrapLengthIsNegative() throws Throwable {
        String separatorCharacter = "|";
        int negativeWrapLength = -2392;
        String newLineString = "|";
        boolean wrapLongWords = false;
        String wrapOn = "|";

        String result = WordUtils.wrap(separatorCharacter, negativeWrapLength, newLineString, wrapLongWords, wrapOn);

        assertEquals("", result);
    }
}
