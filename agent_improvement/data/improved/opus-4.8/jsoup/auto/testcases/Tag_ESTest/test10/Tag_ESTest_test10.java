package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test10 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag has no options set. It should therefore neither
     * preserve whitespace nor be considered a known (pre-defined) tag.
     */
    @Test(timeout = 4000)
    public void newTagPreservesNoWhitespaceAndIsNotKnown() throws Throwable {
        Tag tag = new Tag("", "");

        assertFalse("a new tag should not preserve whitespace", tag.preserveWhitespace());
        assertFalse("a new tag should not be a known tag", tag.isKnownTag());
    }
}
