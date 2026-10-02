package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test38 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that a Tag looked up from the TagSet via valueOf() and a Tag constructed directly
     * via new Tag() are NOT equal, even when they share the same tag name "I".
     *
     * Tag.valueOf("I") returns the pre-registered HTML <i> tag from the TagSet, which has the
     * Known option flag set. new Tag("I") creates a standalone Tag with no options set (not Known).
     * Because equals() compares the options field, the two tags are considered unequal.
     */
    @Test(timeout = 4000)
    public void test38() throws Throwable {
        // Look up the known HTML <i> tag from the TagSet using the uppercase name "I"
        Tag knownItalicTag = Tag.valueOf("I");

        // Construct a standalone Tag with the same name — not registered in any TagSet
        Tag standaloneItalicTag = new Tag("I");

        // The valueOf() result is a known (pre-registered) tag; the directly constructed tag is not
        assertTrue(knownItalicTag.isKnownTag());

        // Both tags resolve to the HTML namespace and lowercase normal name regardless of how they were created
        assertEquals("http://www.w3.org/1999/xhtml", standaloneItalicTag.namespace());
        assertEquals("i", standaloneItalicTag.normalName());

        // They are NOT equal because knownItalicTag has the Known option bit set while standaloneItalicTag does not
        boolean tagsAreEqual = knownItalicTag.equals(standaloneItalicTag);
        assertFalse(tagsAreEqual);

        // Inequality is symmetric
        assertFalse(standaloneItalicTag.equals((Object) knownItalicTag));
    }
}
