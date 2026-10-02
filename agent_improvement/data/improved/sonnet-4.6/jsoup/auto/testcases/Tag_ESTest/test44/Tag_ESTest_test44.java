package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test44 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that a tag created with a custom namespace via preserveCase settings:
     * - normalName() returns the lower-cased tag name, regardless of original casing
     * - namespace() preserves the original namespace string as provided
     * - isKnownTag() is false because the tag was not registered in a predefined TagSet
     */
    @Test(timeout = 4000)
    public void test44() throws Throwable {
        ParseSettings preserveCaseSettings = ParseSettings.preserveCase;
        Tag customNamespacedTag = Tag.valueOf("MJ", "MJ", preserveCaseSettings);

        String lowercasedName = customNamespacedTag.normalName();

        assertEquals("mj", lowercasedName);
        assertEquals("MJ", customNamespacedTag.namespace());
        assertFalse(customNamespacedTag.isKnownTag());
    }
}
