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
public class Regex_ESTest_test0 extends Regex_ESTest_scaffolding {

    // Verifies that the re2j library is present on the classpath, making the fast linear-time
    // regex engine available for jsoup to use.
    @Test(timeout = 4000)
    public void test_hasRe2j_returnsTrueWhenRe2jIsOnClasspath() throws Throwable {
        boolean re2jAvailable = Regex.hasRe2j();
        assertTrue(re2jAvailable);
    }
}
