package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test13 extends Tag_ESTest_scaffolding {

    // "h6" is a standard HTML heading tag and should be recognized as a known tag by jsoup's TagSet
    @Test(timeout = 4000)
    public void test_h6IsAKnownHtmlTag() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");
        boolean isKnown = h6Tag.isKnownTag();
        assertTrue(isKnown);
    }
}
