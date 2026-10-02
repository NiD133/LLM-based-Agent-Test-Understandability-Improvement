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
public class Regex_ESTest_test2 extends Regex_ESTest_scaffolding {

    private static final String LITERAL_PATTERN = "&f}HZ;:/IF8@";

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        Regex.wantsRe2j(false);

        Regex compiledRegex = Regex.compile(LITERAL_PATTERN);

        assertEquals(LITERAL_PATTERN, compiledRegex.toString());
    }
}
