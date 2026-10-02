package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test32 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that a tag whose name contains a colon (e.g. "prefix:localName") correctly
     * reports the local name (the part after the first colon), the default HTML namespace,
     * the fully lower-cased normal name, and that a user-constructed tag is not a known tag.
     *
     * Tag name "8:<KN-^%U" is structured as:
     *   prefix    = "8"
     *   localName = "<KN-^%U"  (everything after the first ':')
     *   normalName = "8:<kn-^%u" (lower-cased full tag name)
     */
    @Test(timeout = 4000)
    public void test_localNameExtractedFromColonSeparatedTagName() throws Throwable {
        // Tag name with a colon: prefix "8" and local name "<KN-^%U"
        String tagNameWithPrefix = "8:<KN-^%U";
        Tag tagWithPrefix = new Tag(tagNameWithPrefix);

        // localName() returns the substring after the first ':'
        String localName = tagWithPrefix.localName();
        assertEquals("<KN-^%U", localName);

        // A Tag created without an explicit namespace defaults to the HTML namespace
        assertEquals("http://www.w3.org/1999/xhtml", tagWithPrefix.namespace());

        // normalName() is the fully lower-cased version of the original tag name
        assertEquals("8:<kn-^%u", tagWithPrefix.normalName());

        // A Tag constructed directly (not looked up in a TagSet) is not a known tag
        assertFalse(tagWithPrefix.isKnownTag());
    }
}
