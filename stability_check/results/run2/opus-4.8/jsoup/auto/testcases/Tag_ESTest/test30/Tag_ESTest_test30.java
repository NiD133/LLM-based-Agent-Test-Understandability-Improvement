package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test30 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag has no options set, so it is not treated as a
     * known tag. Querying its text tokeniser state must not flip that status.
     */
    @Test(timeout = 4000)
    public void freshTagRemainsUnknownAfterTextStateQuery() throws Throwable {
        Tag tag = new Tag("", "");

        tag.textState();

        assertFalse("A newly created tag should not be reported as a known tag",
                tag.isKnownTag());
    }
}
