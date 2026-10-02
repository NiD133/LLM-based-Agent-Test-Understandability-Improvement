package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test3 extends Regex_ESTest_scaffolding {

    /**
     * Wrapping a JDK Pattern via {@link Regex#fromPattern(Pattern)} should yield a
     * Regex whose {@link Regex#matcher(CharSequence)} returns a usable Matcher instance.
     */
    @Test(timeout = 4000)
    public void matcherFromWrappedPatternIsNotNull() throws Throwable {
        String regexSource = "=;r'm!";
        Pattern jdkPattern = Pattern.compile(regexSource);

        Regex regex = Regex.fromPattern(jdkPattern);
        Regex.Matcher matcher = regex.matcher(regexSource);

        assertNotNull(matcher);
    }
}
