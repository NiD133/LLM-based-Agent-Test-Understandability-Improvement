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
        String regexText = "=;r'm!";
        Pattern compiledPattern = Pattern.compile(regexText);

        Regex regex = Regex.fromPattern(compiledPattern);
        Regex.Matcher matcher = regex.matcher(regexText);

        assertNotNull(matcher);
    }
}
