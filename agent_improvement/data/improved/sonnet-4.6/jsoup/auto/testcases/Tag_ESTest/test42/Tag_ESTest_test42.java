package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test42 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test42_tagCreatedWithEmptyNameIsNotKnown() throws Throwable {
        // A Tag constructed directly (not via TagSet) is not a "known" tag,
        // even when both tagName and namespace are empty strings.
        Tag emptyNameTag = new Tag("", "");

        String tagName = emptyNameTag.name();
        assertNotNull("name() should never return null even for an empty-name tag", tagName);
        assertFalse("A directly constructed Tag should not be marked as known", emptyNameTag.isKnownTag());
    }
}
