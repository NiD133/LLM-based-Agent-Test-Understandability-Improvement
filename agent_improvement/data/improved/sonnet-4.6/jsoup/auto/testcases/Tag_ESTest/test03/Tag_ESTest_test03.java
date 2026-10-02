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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Create a tag directly via the internal constructor (tagName, normalName, namespace)
        // and verify that a clone is equal to the original but not considered a known tag
        Tag originalTag = new Tag(" |eOx/>T!", " |eOx/>T!", " |eOx/>T!");
        Tag clonedTag = originalTag.clone();

        assertTrue(originalTag.equals(clonedTag));
        assertFalse(clonedTag.isKnownTag());
    }
}
