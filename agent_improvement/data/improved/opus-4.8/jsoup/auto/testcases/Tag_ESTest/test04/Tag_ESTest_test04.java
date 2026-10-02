package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test04 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag should be equal to itself (reflexive equals),
     * and should not be flagged as a known tag until it is registered or has
     * options applied.
     */
    @Test(timeout = 4000)
    public void newTagEqualsItselfAndIsNotKnown() throws Throwable {
        Tag tag = new Tag("]", "]");

        assertTrue("A tag must be equal to itself", tag.equals(tag));
        assertFalse("A newly created tag is not a known tag", tag.isKnownTag());
    }
}
