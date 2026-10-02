package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test04 extends TokenQueue_ESTest_scaffolding {

    /**
     * A non-ASCII character (code point >= U+0080) is already a valid CSS
     * identifier character, so escapeCssIdentifier should return it unchanged
     * (no backslash escaping applied).
     */
    @Test(timeout = 4000)
    public void escapeCssIdentifier_keepsNonAsciiCharacterUnchanged() throws Throwable {
        // U+008C: a non-ASCII control character, above the U+0080 boundary.
        String nonAsciiInput = String.valueOf((char) 0x8C);

        String escaped = TokenQueue.escapeCssIdentifier(nonAsciiInput);

        assertEquals(nonAsciiInput, escaped);
    }
}
