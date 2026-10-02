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
     * Wrapping a JDK {@link Pattern} via {@link Regex#fromPattern(Pattern)} should yield a
     * {@link Regex} that can produce a non-null matcher for a given input string.
     */
    @Test(timeout = 4000)
    public void matcherFromWrappedJdkPatternIsNotNull() throws Throwable {
        String regexInput = "=;r'm!";
        Pattern jdkPattern = Pattern.compile(regexInput);

        Regex regex = Regex.fromPattern(jdkPattern);
        Regex.Matcher matcher = regex.matcher(regexInput);

        assertNotNull(matcher);
    }
}
