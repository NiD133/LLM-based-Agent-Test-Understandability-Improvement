package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test11 extends Tag_ESTest_scaffolding {

    // "var" is a standard HTML inline element for variables; it must be in the built-in HTML tag set.
    @Test(timeout = 4000)
    public void test_varIsAKnownHtmlTag() throws Throwable {
        boolean varTagIsKnown = Tag.isKnownTag("var");
        assertTrue(varTagIsKnown);
    }
}
