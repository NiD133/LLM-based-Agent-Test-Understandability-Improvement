package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test09 extends TagSet_ESTest_scaffolding {

    /**
     * Verifies that valueOf() on an unknown tag creates a brand-new Tag, and that
     * copying a TagSet produces an equal TagSet.
     *
     * The tag name used here mixes upper- and lower-case letters so we can confirm
     * how the tag preserves the original casing while exposing a lower-cased normal name.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Both the case-sensitive tag name and the namespace are this same mixed-case string.
        String mixedCaseName = "~m&2\"v*M>Y$C[<";
        String lowerCaseName = "~m&2\"v*m>y$c[<";

        TagSet tagSet = new TagSet();

        // The tag is unknown to the (empty) set, so valueOf() creates and registers a new Tag.
        Tag tag = tagSet.valueOf(mixedCaseName, mixedCaseName);
        assertNotNull(tag);

        // normalName() is the lower-cased form; toString()/namespace() keep the original casing.
        assertEquals(lowerCaseName, tag.normalName());
        assertEquals(mixedCaseName, tag.toString());
        assertEquals(mixedCaseName, tag.namespace());

        // A copy of the TagSet holds the same tags and therefore compares equal to the original.
        TagSet copy = new TagSet(tagSet);
        assertTrue(copy.equals(tagSet));
    }
}
