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

    private static final boolean USE_JDK_REGEX_ENGINE = false;
    private static final String INVALID_UNCLOSED_CHARACTER_CLASS = "w%-9C1X&IB[`L";
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Expecting exception: IllegalArgumentException";

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Regex.wantsRe2j(USE_JDK_REGEX_ENGINE);

        try {
            Regex.compile(INVALID_UNCLOSED_CHARACTER_CLASS);
            fail(EXPECTED_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Regex", e);
        }
    }
}
