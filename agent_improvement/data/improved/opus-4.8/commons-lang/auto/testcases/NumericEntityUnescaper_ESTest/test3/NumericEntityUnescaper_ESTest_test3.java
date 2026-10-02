package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NumericEntityUnescaper_ESTest_test3 extends NumericEntityUnescaper_ESTest_scaffolding {

    /**
     * When the input looks like the start of a hex numeric entity ("&#X...") but no
     * valid hex digits follow and no terminating semicolon is present, the default
     * unescaper (which requires a semicolon) leaves the input untranslated. The four
     * trailing characters are NUL ('\\u0000'), so the output equals the original
     * 7-character sequence verbatim.
     */
    @Test(timeout = 4000)
    public void translateMalformedHexEntityWithoutSemicolonReturnsInputUnchanged() throws Throwable {
        // Default behaviour: an empty options array means a semicolon is required.
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);

        // Build "&#X" followed by four NUL characters (the default char[] contents).
        char[] inputChars = new char[7];
        inputChars[0] = '&';
        inputChars[1] = '#';
        inputChars[2] = 'X';
        CharBuffer input = CharBuffer.wrap(inputChars);

        String result = unescaper.translate((CharSequence) input);

        assertEquals("&#X\\u0000\\u0000\\u0000\\u0000", result);
    }
}
