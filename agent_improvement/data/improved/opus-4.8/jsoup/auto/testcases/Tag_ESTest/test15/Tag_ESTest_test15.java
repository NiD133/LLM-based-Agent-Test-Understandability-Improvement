package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test15 extends Tag_ESTest_scaffolding {

    /**
     * A Tag built directly with the single-argument constructor (name only) is an
     * unconfigured, generic tag: it has no options set. This test verifies the
     * default state of such a tag created from the upper-case name "A".
     */
    @Test(timeout = 4000)
    public void newTagFromNameHasDefaultProperties() throws Throwable {
        Tag tag = new Tag("A");

        // The normal name is the lower-cased form of the supplied tag name.
        assertEquals("a", tag.normalName());

        // No options are set, so the tag is neither self-closing nor known,
        // and it defaults to the HTML namespace.
        assertFalse(tag.isSelfClosing());
        assertFalse(tag.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", tag.namespace());
    }
}
