package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test03 extends Tag_ESTest_scaffolding {

    private static final String UNKNOWN_TAG_VALUE = " |eOx/>T!";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Tag originalTag = new Tag(UNKNOWN_TAG_VALUE, UNKNOWN_TAG_VALUE, UNKNOWN_TAG_VALUE);
        Tag clonedTag = originalTag.clone();

        boolean cloneMatchesOriginal = originalTag.equals(clonedTag);

        assertTrue(cloneMatchesOriginal);
        assertFalse(clonedTag.isKnownTag());
    }
}
