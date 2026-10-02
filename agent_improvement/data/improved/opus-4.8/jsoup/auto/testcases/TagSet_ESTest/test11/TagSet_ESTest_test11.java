package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test11 extends TagSet_ESTest_scaffolding {

    /**
     * Verifies that {@link TagSet#initHtmlDefault()} builds and returns the
     * default HTML tag set rather than returning null.
     */
    @Test(timeout = 4000)
    public void initHtmlDefaultReturnsNonNullTagSet() throws Throwable {
        TagSet defaultHtmlTags = TagSet.initHtmlDefault();

        assertNotNull(defaultHtmlTags);
    }
}
