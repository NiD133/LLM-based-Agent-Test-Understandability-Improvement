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

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        // Create a tag from a mixed-case name with special characters; it is not a known HTML tag
        Tag unknownTag = Tag.valueOf("3urw0GS^ `H");

        // Clearing the Known option returns the same tag instance (fluent API)
        Tag tagWithKnownCleared = unknownTag.clear(Tag.Known);

        // After explicitly clearing the Known bit, the tag is no longer recognized as known
        assertFalse(unknownTag.isKnownTag());

        // The normal name is the lowercased version of the original tag name
        assertEquals("3urw0gs^ `h", tagWithKnownCleared.normalName());

        // No structural or whitespace-related options are set on the tag
        assertFalse(tagWithKnownCleared.isSelfClosing());
        assertFalse(tagWithKnownCleared.preserveWhitespace());
        assertFalse(tagWithKnownCleared.formatAsBlock());

        // The tag lives in the HTML namespace and is treated as an inline (non-block) element
        assertEquals("http://www.w3.org/1999/xhtml", tagWithKnownCleared.namespace());
        assertTrue(tagWithKnownCleared.isInline());

        // The tag is not a form-submittable element
        assertFalse(tagWithKnownCleared.isFormSubmittable());
    }
}
