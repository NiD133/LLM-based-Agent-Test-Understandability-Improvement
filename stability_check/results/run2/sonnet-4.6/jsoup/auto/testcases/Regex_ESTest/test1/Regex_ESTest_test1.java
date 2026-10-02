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

    /**
     * Verifies that compiling a pattern with an unclosed character class throws
     * an IllegalArgumentException when re2j is disabled (JDK regex engine is used).
     *
     * The pattern "w%-9C1X&IB[`L" contains an unclosed '[' at index 10,
     * which the JDK regex engine rejects as a PatternSyntaxException wrapped
     * into a ValidationException (which extends IllegalArgumentException).
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Force the JDK regex engine by disabling re2j
        Regex.wantsRe2j(false);

        // A pattern with an unclosed character class '[' that should fail to compile
        String patternWithUnclosedCharacterClass = "w%-9C1X&IB[`L";

        try {
            Regex.compile(patternWithUnclosedCharacterClass);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Pattern syntax error: Unclosed character class near index 12
            // w%-9C1X&IB[`L
            //             ^
            //
            verifyException("org.jsoup.helper.Regex", e);
        }
    }
}
