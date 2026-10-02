package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test02 extends Tag_ESTest_scaffolding {

    private static final String HTML_TAG_NAME = "html";
    private static final String CUSTOM_TAG_NAME = "7E$B_fi2(/F6\"]&R$!6";
    private static final String NORMALIZED_CUSTOM_TAG_NAME = "7e$b_fi2(/f6\"]&r$!6";
    private static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Tag htmlTag = Tag.valueOf(HTML_TAG_NAME);
        Tag customTag = new Tag(CUSTOM_TAG_NAME);

        boolean tagsAreEqual = htmlTag.equals(customTag);

        assertFalse(tagsAreEqual);
        assertTrue(htmlTag.isKnownTag());
        assertEquals(NORMALIZED_CUSTOM_TAG_NAME, customTag.normalName());
        assertFalse(customTag.isKnownTag());
        assertEquals(HTML_NAMESPACE, customTag.namespace());
    }
}
