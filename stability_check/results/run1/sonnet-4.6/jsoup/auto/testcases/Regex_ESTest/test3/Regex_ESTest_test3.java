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

    // Verifies that wrapping an existing JDK Pattern via fromPattern() produces a usable
    // Regex whose matcher() method returns a non-null Matcher for the given input.
    @Test(timeout = 4000)
    public void test_fromPattern_matcherIsNotNull() throws Throwable {
        Pattern jdkPattern = Pattern.compile("=;r'm!");
        Regex wrappedRegex = Regex.fromPattern(jdkPattern);
        Regex.Matcher matcher = wrappedRegex.matcher("=;r'm!");
        assertNotNull(matcher);
    }
}
