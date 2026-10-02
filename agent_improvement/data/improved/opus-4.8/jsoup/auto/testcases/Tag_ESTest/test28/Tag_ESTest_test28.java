package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test28 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that clearing the {@code Known} option on an unknown, mixed-case tag
     * keeps its normalized (lower-cased) name and leaves all the default layout/parse
     * options unset.
     */
    @Test(timeout = 4000)
    public void clearingKnownOptionKeepsDefaultTagProperties() throws Throwable {
        // An unknown tag whose name mixes upper and lower case characters.
        Tag tag = Tag.valueOf("3urw0GS^ `H");

        // Clearing the Known option returns the same tag for chaining.
        Tag cleared = tag.clear(Tag.Known);

        // The normalized name is always the lower-cased form of the original name.
        assertEquals("3urw0gs^ `h", cleared.normalName());

        // A freshly created, unknown tag carries the default HTML namespace.
        assertEquals("http://www.w3.org/1999/xhtml", cleared.namespace());

        // With the Known option cleared, the tag is still treated as not known.
        assertFalse(tag.isKnownTag());

        // None of the layout or parse-related options are set by default.
        assertFalse(cleared.isSelfClosing());
        assertFalse(cleared.preserveWhitespace());
        assertFalse(cleared.formatAsBlock());
        assertFalse(cleared.isFormSubmittable());

        // Without the Block option, the tag is inline.
        assertTrue(cleared.isInline());
    }
}
