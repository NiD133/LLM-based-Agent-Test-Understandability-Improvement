package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test26 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag (with empty name and namespace) has no parser
     * options enabled, so hasParserOption returns false, and because no option
     * has been set it is not yet a known tag.
     */
    @Test(timeout = 4000)
    public void newTagHasNoParserOptionsAndIsNotKnown() throws Throwable {
        Tag tag = new Tag("", "");

        boolean hasOption = tag.hasParserOption(128);

        assertFalse("A new tag should not be marked as known", tag.isKnownTag());
        assertFalse("A new tag should have no parser options set", hasOption);
    }
}
