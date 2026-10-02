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
     * A predefined HTML tag and a freshly constructed unknown tag should not be equal,
     * and only the predefined one should be reported as "known". The unknown tag still
     * gets a lower-cased normal name and defaults to the HTML namespace.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Tag knownHtmlTag = Tag.valueOf("html");
        Tag unknownTag = new Tag("7E$B_fi2(/F6\"]&R$!6");

        boolean tagsAreEqual = knownHtmlTag.equals(unknownTag);
        assertFalse(tagsAreEqual);

        assertTrue(knownHtmlTag.isKnownTag());

        assertEquals("7e$b_fi2(/f6\"]&r$!6", unknownTag.normalName());
        assertFalse(unknownTag.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", unknownTag.namespace());
    }
}
