package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test11 extends Tag_ESTest_scaffolding {

    /**
     * "var" is a standard HTML tag, so {@link Tag#isKnownTag(String)} should
     * recognise it as a known tag.
     */
    @Test(timeout = 4000)
    public void isKnownTag_returnsTrue_forStandardHtmlTagVar() throws Throwable {
        boolean isKnown = Tag.isKnownTag("var");

        assertTrue("\"var\" is a standard HTML tag and should be recognised as known", isKnown);
    }
}
