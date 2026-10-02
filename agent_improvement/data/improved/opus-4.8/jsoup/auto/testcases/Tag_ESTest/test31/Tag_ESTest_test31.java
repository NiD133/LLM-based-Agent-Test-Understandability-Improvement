package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test31 extends Tag_ESTest_scaffolding {

    /**
     * A Tag created from a raw, unrecognized name (with no ':' prefix) keeps that
     * name verbatim as its local name, derives a lower-cased normal name, defaults
     * to the HTML namespace, and is not treated as a known tag.
     */
    @Test(timeout = 4000)
    public void newTagFromUnknownNameExposesNameAndDefaults() throws Throwable {
        Tag tag = new Tag("<KN-^%U");

        // No ':' prefix, so the local name equals the original tag name.
        assertEquals("<KN-^%U", tag.localName());
        // Normal name is the lower-cased form of the tag name.
        assertEquals("<kn-^%u", tag.normalName());
        // Single-argument constructor defaults to the HTML namespace.
        assertEquals("http://www.w3.org/1999/xhtml", tag.namespace());
        // An ad-hoc tag not registered in any TagSet is not a known tag.
        assertFalse(tag.isKnownTag());
    }
}
