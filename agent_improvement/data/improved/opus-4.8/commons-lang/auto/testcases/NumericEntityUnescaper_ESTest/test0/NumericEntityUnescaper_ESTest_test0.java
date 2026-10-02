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
public class NumericEntityUnescaper_ESTest_test0 extends NumericEntityUnescaper_ESTest_scaffolding {

    /**
     * Verifies that a well-formed decimal numeric entity ("&amp;#2;") is unescaped to its
     * corresponding code point (U+0002), while any trailing characters are copied through
     * unchanged.
     *
     * <p>The input is a 7-character array holding the entity "&amp;#2;" followed by three
     * unset slots that default to the null character (U+0000). With the default options
     * (semicolon required), the entity becomes U+0002 and the three trailing null
     * characters are preserved.</p>
     */
    @Test(timeout = 4000)
    public void translateDecimalEntityUnescapesCodePointAndKeepsTrailingChars() throws Throwable {
        // No options: defaults to OPTION.semiColonRequired.
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);

        // Input "&#2;" plus three trailing null characters from the array defaults.
        char[] inputChars = new char[7];
        inputChars[0] = '&';
        inputChars[1] = '#';
        inputChars[2] = '2';
        inputChars[3] = ';';
        CharBuffer input = CharBuffer.wrap(inputChars);

        String result = unescaper.translate((CharSequence) input);

        // Expected: U+0002 (decoded from "&#2;") followed by three U+0000 (NUL) characters.
        assertEquals("\\u0002\\u0000\\u0000\\u0000", result);
    }
}
