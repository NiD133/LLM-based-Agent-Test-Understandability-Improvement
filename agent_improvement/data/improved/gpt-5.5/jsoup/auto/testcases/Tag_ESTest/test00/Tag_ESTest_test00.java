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

    private static final String ORIGINAL_TAG_NAME = " |eOx/>T!";
    private static final String RENAMED_TAG_NAME = "b5 ;cl";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Tag originalTag = new Tag(ORIGINAL_TAG_NAME, ORIGINAL_TAG_NAME, ORIGINAL_TAG_NAME);
        originalTag.tagName = RENAMED_TAG_NAME;

        Tag clonedTag = originalTag.clone();
        clonedTag.name(RENAMED_TAG_NAME);

        boolean tagsAreEqual = originalTag.equals(clonedTag);

        assertEquals(RENAMED_TAG_NAME, clonedTag.normalName());
        assertFalse(tagsAreEqual);
    }
}
