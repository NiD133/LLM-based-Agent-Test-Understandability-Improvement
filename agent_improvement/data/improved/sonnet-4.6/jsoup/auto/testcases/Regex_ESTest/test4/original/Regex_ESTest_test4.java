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

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        Regex regex0 = Regex.compile("VERTICAL_BAR");
        assertTrue(regex0.usingRe2j());
    }
}
