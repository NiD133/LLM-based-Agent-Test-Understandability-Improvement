package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test1 extends Regex_ESTest_scaffolding {

    /**
     * When re2j is disabled, {@link Regex#compile(String)} falls back to the JDK regex engine.
     * Compiling a pattern with an unclosed character class ('[') should make the JDK engine
     * throw a PatternSyntaxException, which Regex re-wraps as a ValidationException
     * (an IllegalArgumentException) originating from the Regex class.
     */
    @Test(timeout = 4000)
    public void compileInvalidRegexThrowsValidationException() throws Throwable {
        // Force use of the JDK regex engine rather than re2j.
        Regex.wantsRe2j(false);

        String invalidRegex = "w%-9C1X&IB[`L"; // '[' opens a character class that is never closed

        try {
            Regex.compile(invalidRegex);
            fail("Expected an IllegalArgumentException for the unclosed character class");
        } catch (IllegalArgumentException expected) {
            // The exception must be raised (and wrapped) by Regex itself.
            verifyException("org.jsoup.helper.Regex", expected);
        }
    }
}
