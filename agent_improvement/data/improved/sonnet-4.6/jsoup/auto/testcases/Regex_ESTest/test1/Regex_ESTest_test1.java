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

    // A regex pattern with an unclosed character class '[' — guaranteed to cause a PatternSyntaxException
    private static final String UNCLOSED_CHARACTER_CLASS_PATTERN = "w%-9C1X&IB[`L";

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Force use of the JDK regex engine so PatternSyntaxException is wrapped into IllegalArgumentException
        Regex.wantsRe2j(false);

        try {
            Regex.compile(UNCLOSED_CHARACTER_CLASS_PATTERN);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Regex.compile wraps PatternSyntaxException as "Pattern syntax error: Unclosed character class near index 12"
            verifyException("org.jsoup.helper.Regex", e);
        }
    }
}
