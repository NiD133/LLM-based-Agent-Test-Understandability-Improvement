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
public class Regex_ESTest_test5 extends Regex_ESTest_scaffolding {

    // Verifies that Regex.toString() delegates to the underlying JDK Pattern's toString(),
    // returning the original pattern string used to create the Pattern.
    @Test(timeout = 4000)
    public void test_toStringReturnsUnderlyingPatternString() throws Throwable {
        String patternString = "VERTICAL_BAR";
        Pattern jdkPattern = Pattern.compile(patternString);
        Regex regex = Regex.fromPattern(jdkPattern);

        String result = regex.toString();

        assertEquals(patternString, result);
    }
}
