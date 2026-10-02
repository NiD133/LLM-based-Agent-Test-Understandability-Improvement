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
     * A freshly constructed Tag has no options set. Querying its text state does
     * not mark it as known, so it should still report as an unknown tag.
     */
    @Test(timeout = 4000)
    public void freshTagIsNotKnownAfterQueryingTextState() throws Throwable {
        Tag freshTag = new Tag("", "");

        freshTag.textState();

        assertFalse("A newly created tag with no options set should not be known",
                freshTag.isKnownTag());
    }
}
