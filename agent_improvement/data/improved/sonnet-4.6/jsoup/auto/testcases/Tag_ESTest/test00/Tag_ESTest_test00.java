package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test00 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that two Tags with the same tagName are NOT equal when their normalNames differ.
     *
     * The key scenario:
     * - tag0 has its tagName set via direct field assignment, which does NOT update normalName.
     * - tag1 is a clone of tag0, then has its name changed via the name() method, which DOES update normalName.
     * - equals() compares both tagName and normalName, so the Tags are unequal despite sharing the same tagName.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Create a tag using the package-private constructor (tagName, normalName, namespace are all the same raw value).
        Tag originalTag = new Tag(" |eOx/>T!", " |eOx/>T!", " |eOx/>T!");

        // Directly assign tagName via field access — this bypasses the name() setter,
        // so normalName remains " |eOx/>T!" (stale / out of sync with tagName).
        originalTag.tagName = "b5 ;cl";

        // Clone the tag: the clone starts with tagName="b5 ;cl" and the stale normalName=" |eOx/>T!".
        Tag clonedTag = originalTag.clone();

        // Update the clone through the proper setter, which syncs both tagName and normalName to "b5 ;cl".
        clonedTag.name("b5 ;cl");

        // originalTag.normalName is still " |eOx/>T!" (never synced), while clonedTag.normalName is "b5 ;cl".
        // equals() requires normalName to match, so the tags are not equal.
        boolean tagsAreEqual = originalTag.equals(clonedTag);

        assertEquals("b5 ;cl", clonedTag.normalName());
        assertFalse(tagsAreEqual);
    }
}
