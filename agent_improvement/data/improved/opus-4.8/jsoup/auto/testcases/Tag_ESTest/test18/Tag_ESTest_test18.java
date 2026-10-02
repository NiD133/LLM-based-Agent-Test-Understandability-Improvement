package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test18 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag has no options set, so it should be neither
     * an empty (void) tag nor a known tag.
     */
    @Test(timeout = 4000)
    public void newTagIsNeitherEmptyNorKnown() throws Throwable {
        Tag tag = new Tag("", "");

        assertFalse("a new tag should not be a void/empty tag", tag.isEmpty());
        assertFalse("a new tag should not be a known tag", tag.isKnownTag());
    }
}
