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
public class NumericEntityUnescaper_ESTest_test2 extends NumericEntityUnescaper_ESTest_scaffolding {

    /**
     * When no OPTION values are supplied, the constructor defaults to semiColonRequired.
     * An incomplete numeric entity "&#3" that lacks a trailing semicolon must not be
     * translated; it should pass through as literal characters.  The four null characters
     * that precede it in the input are also kept as-is.
     */
    @Test(timeout = 4000)
    public void test_defaultSemiColonRequired_incompleteEntityIsNotTranslated() throws Throwable {
        // Empty options array triggers the semiColonRequired default in the constructor
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);

        // Input: four default null chars ('\0') followed by "&#3" without a closing semicolon
        char[] inputChars = new char[7];   // indices 0-3 are '\0' by default
        inputChars[4] = '&';
        inputChars[5] = '#';
        inputChars[6] = '3';
        CharBuffer inputBuffer = CharBuffer.wrap(inputChars);

        // translate() should leave the incomplete entity untouched because semiColonRequired is set
        String result = unescaper.translate((CharSequence) inputBuffer);

        // Four null chars followed by the literal "&#3" -- nothing was unescaped
        assertEquals("\u0000\u0000\u0000\u0000&#3", result);
    }
}
