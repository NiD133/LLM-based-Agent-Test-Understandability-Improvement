package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test32 extends Tag_ESTest_scaffolding {

    /**
     * Creates a Tag from a prefixed, mixed-case name and verifies the derived
     * properties:
     * <ul>
     *   <li>{@code localName()} drops the prefix before the first ':'</li>
     *   <li>{@code namespace()} defaults to the HTML namespace</li>
     *   <li>{@code normalName()} is the lower-cased full tag name</li>
     *   <li>{@code isKnownTag()} is false for an ad-hoc, never-configured tag</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void prefixedTagExposesLocalNameNamespaceAndNormalName() throws Throwable {
        Tag tag = new Tag("8:<KN-^%U");

        // Local name is the part after the first ':' prefix separator.
        assertEquals("<KN-^%U", tag.localName());

        // No namespace was given, so it defaults to the HTML namespace.
        assertEquals("http://www.w3.org/1999/xhtml", tag.namespace());

        // Normal name is the whole tag name, lower-cased.
        assertEquals("8:<kn-^%u", tag.normalName());

        // The tag was never added to a TagSet or given options, so it is unknown.
        assertFalse(tag.isKnownTag());
    }
}
