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

    /**
     * Verifies that a known HTML tag (html) is not equal to a custom unknown tag,
     * and that the custom tag's normal name is lowercased and its namespace defaults to HTML.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Tag htmlTag = Tag.valueOf("html");
        Tag customTag = new Tag("7E$B_fi2(/F6\"]&R$!6");

        boolean tagsAreEqual = htmlTag.equals(customTag);

        assertFalse(tagsAreEqual);
        assertTrue(htmlTag.isKnownTag());
        assertEquals("7e$b_fi2(/f6\"]&r$!6", customTag.normalName());
        assertFalse(customTag.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", customTag.namespace());
    }
}
