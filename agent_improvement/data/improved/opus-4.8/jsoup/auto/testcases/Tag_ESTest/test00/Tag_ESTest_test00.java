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
     * Two tags are unequal when their normalized names differ, even if their
     * (case-sensitive) tag names match.
     *
     * The original tag is built with one literal value used for tag name,
     * normal name, and namespace, then its tagName field is overwritten
     * directly (leaving normalName unchanged). The clone inherits that stale
     * normalName, but calling name(...) recomputes the clone's normalName from
     * the new tag name. So the two tags end up with equal tag names but
     * different normal names, and equals() returns false.
     */
    @Test(timeout = 4000)
    public void differentNormalNameMakesTagsUnequal() throws Throwable {
        String literal = " |eOx/>T!";
        String renamed = "b5 ;cl";

        Tag originalTag = new Tag(literal, literal, literal);
        // Overwrite only the tag name; normalName stays as the original literal.
        originalTag.tagName = renamed;

        // Clone copies the stale normalName, then name() recomputes it from the new name.
        Tag clonedTag = originalTag.clone();
        clonedTag.name(renamed);

        boolean tagsAreEqual = originalTag.equals(clonedTag);

        // The clone's normalName was recomputed from the renamed tag name.
        assertEquals(renamed, clonedTag.normalName());
        // Differing normalName values make the two tags unequal.
        assertFalse(tagsAreEqual);
    }
}
