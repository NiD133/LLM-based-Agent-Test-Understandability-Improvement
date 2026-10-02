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

    private static final String MALFORMED_CHARACTER_CLASS_REGEX = "w%-9C1X&IB[`L";
    private static final String EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION = "Expecting exception: IllegalArgumentException";

    @Test(timeout = 4000)
    public void rejectsUnclosedCharacterClassWhenJdkRegexIsUsed() throws Throwable {
        Regex.wantsRe2j(false);

        try {
            Regex.compile(MALFORMED_CHARACTER_CLASS_REGEX);
            fail(EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Regex", e);
        }
    }
}
