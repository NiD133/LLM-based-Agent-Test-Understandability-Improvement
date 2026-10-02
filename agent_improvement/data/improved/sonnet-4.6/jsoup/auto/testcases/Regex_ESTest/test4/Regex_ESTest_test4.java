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
public class Regex_ESTest_test4 extends Regex_ESTest_scaffolding {

    // Verifies that the re2j engine is active when the re2j library is on the classpath.
    // "VERTICAL_BAR" is a plain literal pattern used only to trigger compile(); the real
    // assertion is on the static engine-selection flag, not the compiled pattern itself.
    @Test(timeout = 4000)
    public void test_compileUsesRe2jEngineWhenAvailable() throws Throwable {
        Regex compiledPattern = Regex.compile("VERTICAL_BAR");
        assertTrue(compiledPattern.usingRe2j());
    }
}
