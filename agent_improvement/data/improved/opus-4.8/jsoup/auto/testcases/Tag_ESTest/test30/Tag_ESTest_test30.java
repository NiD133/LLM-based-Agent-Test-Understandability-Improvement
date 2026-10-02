package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test30 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag has no options set, so querying its text
     * tokeniser state is a harmless no-op and the tag is not yet "known".
     */
    @Test(timeout = 4000)
    public void freshlyCreatedTagIsNotKnown() throws Throwable {
        Tag tag = new Tag("", "");

        // textState() inspects the tag's options without modifying them.
        tag.textState();

        // No options have been applied, so the tag is not flagged as known.
        assertFalse(tag.isKnownTag());
    }
}
