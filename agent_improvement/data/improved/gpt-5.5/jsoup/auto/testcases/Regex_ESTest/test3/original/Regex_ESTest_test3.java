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
        Pattern pattern0 = Pattern.compile("=;r'm!");
        Regex regex0 = Regex.fromPattern(pattern0);
        Regex.Matcher regex_Matcher0 = regex0.matcher("=;r'm!");
        assertNotNull(regex_Matcher0);
    }
}
