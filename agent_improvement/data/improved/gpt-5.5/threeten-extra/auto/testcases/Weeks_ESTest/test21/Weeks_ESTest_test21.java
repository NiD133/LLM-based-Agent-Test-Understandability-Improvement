package org.threeten.extra;

import org.junit.Test;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test21 extends Weeks_ESTest_scaffolding {

    private static final String ONE_WEEK_TEXT = "P1W";
    private static final String REGEX_MATCHER_CLASS = "java.util.regex.Matcher";

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        try {
            Weeks.parse(ONE_WEEK_TEXT);
        } catch (IllegalStateException exception) {
            verifyException(REGEX_MATCHER_CLASS, exception);
        }
    }
}
