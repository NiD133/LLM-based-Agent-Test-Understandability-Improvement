package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test38 extends Tag_ESTest_scaffolding {

    /**
     * A tag resolved via {@link Tag#valueOf(String)} is flagged as a known tag, whereas a tag
     * created directly through the constructor is a plain generic tag. Because the two carry
     * different option flags, they are not considered equal even though they share the same name.
     */
    @Test(timeout = 4000)
    public void knownTagDoesNotEqualConstructedGenericTagWithSameName() throws Throwable {
        Tag knownTag = Tag.valueOf("I");
        Tag genericTag = new Tag("I");

        // The looked-up tag is marked as known; the directly constructed one is not.
        assertTrue(knownTag.isKnownTag());

        // The constructed tag defaults to the HTML namespace and a lower-cased normal name.
        assertEquals("http://www.w3.org/1999/xhtml", genericTag.namespace());
        assertEquals("i", genericTag.normalName());

        // Differing option flags make the two tags unequal in both directions.
        assertFalse(knownTag.equals(genericTag));
        assertFalse(genericTag.equals(knownTag));
    }
}
