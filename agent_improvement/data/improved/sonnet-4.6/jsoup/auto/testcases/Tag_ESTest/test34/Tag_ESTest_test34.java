package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test34 extends Tag_ESTest_scaffolding {

    /**
     * Tests that a tag name containing a namespace prefix (e.g. "Oc2:{") correctly
     * returns the prefix portion before the colon, normalises the name to lowercase,
     * defaults to the HTML namespace, and is not treated as a known (built-in) tag.
     */
    @Test(timeout = 4000)
    public void test34() throws Throwable {
        // Create a tag whose name includes a colon-separated prefix "Oc2"
        Tag prefixedTag = Tag.valueOf("Oc2:{");

        // Extract the prefix (the segment before the first colon)
        String prefix = prefixedTag.prefix();

        // The tag is not a predefined HTML tag
        assertFalse(prefixedTag.isKnownTag());

        // The returned prefix must be the original-case segment before the colon
        assertEquals("Oc2", prefix);

        // The normalised name is the full tag name lowercased
        assertEquals("oc2:{", prefixedTag.normalName());

        // Unknown tags default to the HTML namespace
        assertEquals("http://www.w3.org/1999/xhtml", prefixedTag.namespace());
    }
}
