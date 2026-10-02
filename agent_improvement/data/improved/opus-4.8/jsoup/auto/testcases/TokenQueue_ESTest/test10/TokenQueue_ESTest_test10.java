package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test10 extends TokenQueue_ESTest_scaffolding {

    /**
     * escapeCssIdentifier should make a raw string safe to use as a CSS selector identifier:
     *  - A leading "-" immediately followed by a digit must be kept, with the digit escaped as a
     *    hex code point ("8" -> "\38 ", note the trailing space terminating the escape).
     *  - Letters and digits elsewhere are left untouched.
     *  - Special characters such as "#" and "*" are backslash-escaped.
     */
    @Test(timeout = 4000)
    public void escapeCssIdentifier_escapesLeadingDigitAndSpecialChars() throws Throwable {
        String escaped = TokenQueue.escapeCssIdentifier("-8Lc8X#GxA*");

        assertEquals("-\\38 Lc8X\\#GxA\\*", escaped);
    }
}
