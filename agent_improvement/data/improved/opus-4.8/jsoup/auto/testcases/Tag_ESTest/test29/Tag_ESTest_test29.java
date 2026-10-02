package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test29 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that toggling options on a cloned Tag does not affect the original,
     * and that resolving the text-tokeniser state on the clone runs without error.
     */
    @Test(timeout = 4000)
    public void cloneOptionsDoNotAffectOriginalTag() throws Throwable {
        // Create an original tag with empty name and namespace; with no options it is not "known".
        Tag originalTag = new Tag("", "");

        // Clone it, then enable the Data option (value 256) only on the clone.
        Tag clonedTag = originalTag.clone();
        clonedTag.options = 256; // Tag.Data: an element holding raw text data

        // Resolving the text state on the clone should succeed given the Data option.
        clonedTag.textState();

        // The original tag was never touched, so it remains an unknown tag.
        assertFalse(originalTag.isKnownTag());
    }
}
