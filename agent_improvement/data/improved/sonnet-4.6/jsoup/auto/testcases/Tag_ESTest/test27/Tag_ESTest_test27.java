package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test27 extends Tag_ESTest_scaffolding {

    // Clearing any option other than Tag.Known implicitly marks the tag as known.
    @Test(timeout = 4000)
    public void clearingAnyOptionOnUnknownTagMarksItAsKnown() throws Throwable {
        Tag unknownTag = Tag.valueOf("bZJf");
        assertFalse(unknownTag.isKnownTag());

        unknownTag.clear(Tag.FormSubmittable);

        assertTrue(unknownTag.isKnownTag());
    }
}
