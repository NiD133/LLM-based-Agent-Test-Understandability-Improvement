package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test41 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_valueOfWithNullParseSettings_throwsNullPointerException() throws Throwable {
        // Tag.valueOf(tagName, settings) delegates to valueOf(tagName, namespace, settings)
        // which calls settings.preserveTagCase() — so passing null settings must throw NPE.
        try {
            Tag.valueOf("", (ParseSettings) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.jsoup.parser.Tag", e);
        }
    }
}
