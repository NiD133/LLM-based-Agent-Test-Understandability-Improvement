package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test22 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag has no options set, so it is neither an
     * InlineContainer (formatAsBlock) nor a known tag.
     */
    @Test(timeout = 4000)
    public void freshTagHasNoFormatAsBlockOrKnownOptions() throws Throwable {
        Tag tag = new Tag("jh6", "jh6", "jh6");

        assertFalse("a new tag should not be flagged as InlineContainer", tag.formatAsBlock());
        assertFalse("a new tag should not be a known tag", tag.isKnownTag());
    }
}
