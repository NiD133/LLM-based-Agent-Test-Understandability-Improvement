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
public class Regex_ESTest_test3 extends Regex_ESTest_scaffolding {

    /**
     * Verifies that a Regex created from an existing JDK Pattern can produce a
     * Matcher for a given input string.
     */
    @Test(timeout = 4000)
    public void matcherFromPatternReturnsNonNullMatcher() throws Throwable {
        String regexAndInput = "=;r'm!";
        Pattern jdkPattern = Pattern.compile(regexAndInput);

        Regex regex = Regex.fromPattern(jdkPattern);
        Regex.Matcher matcher = regex.matcher(regexAndInput);

        assertNotNull("matcher() should never return null", matcher);
    }
}
