package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test05 extends TagSet_ESTest_scaffolding {

    /**
     * "kbd" is one of the predefined inline HTML tags in the default tag set,
     * so resolving it via Tag.valueOf should yield a tag flagged as "known".
     */
    @Test(timeout = 4000)
    public void valueOf_predefinedHtmlTag_isKnownTag() throws Throwable {
        Tag kbdTag = Tag.valueOf("kbd");

        assertTrue("kbd is a built-in HTML tag and should be recognised as known",
                kbdTag.isKnownTag());
    }
}
