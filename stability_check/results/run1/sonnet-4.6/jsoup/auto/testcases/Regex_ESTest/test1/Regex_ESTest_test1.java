package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.regex.Pattern;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test1 extends Regex_ESTest_scaffolding {

    // A pattern with an unclosed '[' character class, which is syntactically invalid.
    private static final String PATTERN_WITH_UNCLOSED_CHARACTER_CLASS = "w%-9C1X&IB[`L";

    /**
     * When re2j is disabled, Regex.compile() delegates to the JDK regex engine.
     * The JDK engine throws an IllegalArgumentException (via ValidationException)
     * when it encounters a pattern with an unclosed character class.
     */
    @Test(timeout = 4000)
    public void test_compileInvalidPattern_withRe2jDisabled_throwsIllegalArgumentException() throws Throwable {
        // Disable re2j so the JDK regex engine is used for compilation.
        Regex.wantsRe2j(false);

        try {
            Regex.compile(PATTERN_WITH_UNCLOSED_CHARACTER_CLASS);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Regex", e);
        }
    }
}
