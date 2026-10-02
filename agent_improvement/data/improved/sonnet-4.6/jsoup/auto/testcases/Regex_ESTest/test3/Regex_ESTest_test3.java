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

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Wrap an existing JDK Pattern into a Regex and verify that a Matcher can be created from it
        Pattern pattern = Pattern.compile("=;r'm!");
        Regex regex = Regex.fromPattern(pattern);
        Regex.Matcher matcher = regex.matcher("=;r'm!");
        assertNotNull(matcher);
    }
}
