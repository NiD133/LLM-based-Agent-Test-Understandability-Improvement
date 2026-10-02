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

    /**
     * Resolving a standard HTML tag name ("h6") via {@link Tag#valueOf(String)} should
     * yield a predefined tag, which therefore reports itself as a known tag.
     */
    @Test(timeout = 4000)
    public void valueOf_withStandardHtmlTagName_returnsKnownTag() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");

        boolean isKnownTag = h6Tag.isKnownTag();

        assertTrue("h6 is a predefined HTML tag, so it should be reported as known", isKnownTag);
    }
}
