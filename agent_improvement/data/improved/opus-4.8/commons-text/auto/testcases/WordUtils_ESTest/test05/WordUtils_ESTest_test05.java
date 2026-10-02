package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test05 extends WordUtils_ESTest_scaffolding {

    /**
     * When the wrap length is larger than the input text, there is nothing to wrap,
     * so {@link WordUtils#wrap(String, int, String, boolean)} returns the text unchanged.
     * Here the input has no spaces and is far shorter than the wrap length of 259.
     */
    @Test(timeout = 4000)
    public void wrapReturnsTextUnchangedWhenWrapLengthExceedsTextLength() throws Throwable {
        String input = "?*!2g,cNA6z2~8(~C,";
        int wrapLength = 259;
        String newLineString = "*|";
        boolean wrapLongWords = true;

        String wrapped = WordUtils.wrap(input, wrapLength, newLineString, wrapLongWords);

        assertEquals("?*!2g,cNA6z2~8(~C,", wrapped);
    }
}
