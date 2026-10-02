package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test35 extends TokenQueue_ESTest_scaffolding {

    /**
     * escapeCssIdentifier should escape a control character (here the backspace,
     * U+0008) as a CSS code point: a backslash, the hex value of the code point,
     * and a trailing space. The surrounding letters/digits are valid identifier
     * characters and are left unchanged.
     */
    @Test(timeout = 4000)
    public void escapesControlCharacterAsCssCodePoint() throws Throwable {
        String input = "p\bmoD0M"; // \b is the backspace control character (U+0008)

        String escaped = TokenQueue.escapeCssIdentifier(input);

        // The backspace becomes "\8 " (backslash, hex code point "8", trailing space).
        assertEquals("p\\8 moD0M", escaped);
    }
}
