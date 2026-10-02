package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test31 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        // Create a tag with a non-standard, mixed-case name that has no colon prefix
        String originalTagName = "<KN-^%U";
        Tag tag = new Tag(originalTagName);

        // localName() returns the full tag name when there is no colon-separated prefix
        String localName = tag.localName();
        assertEquals(originalTagName, localName);

        // Tags created via the constructor are not registered in a TagSet, so isKnownTag() is false
        assertFalse(tag.isKnownTag());

        // The single-argument constructor places the tag in the HTML namespace by default
        assertEquals("http://www.w3.org/1999/xhtml", tag.namespace());

        // normalName() always returns the lowercased form of the tag name
        assertEquals("<kn-^%u", tag.normalName());
    }
}
