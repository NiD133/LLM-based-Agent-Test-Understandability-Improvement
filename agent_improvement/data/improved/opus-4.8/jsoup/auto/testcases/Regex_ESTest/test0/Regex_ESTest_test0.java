package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test0 extends Regex_ESTest_scaffolding {

    /**
     * The re2j library is present on the test classpath, so {@link Regex#hasRe2j()}
     * should detect it and report that the re2j engine is available.
     */
    @Test(timeout = 4000)
    public void hasRe2jReturnsTrueWhenRe2jIsOnClasspath() throws Throwable {
        boolean re2jAvailable = Regex.hasRe2j();

        assertTrue("re2j should be detected on the classpath", re2jAvailable);
    }
}
