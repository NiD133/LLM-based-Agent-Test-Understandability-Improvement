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

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Regex.wantsRe2j(false);
        // Undeclared exception!
        try {
            Regex.compile("w%-9C1X&IB[`L");
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
