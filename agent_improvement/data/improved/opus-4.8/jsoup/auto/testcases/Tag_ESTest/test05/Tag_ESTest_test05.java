package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test05 extends Tag_ESTest_scaffolding {

    /**
     * A Tag is never equal to a non-Tag object, and a freshly constructed Tag
     * (with no options applied) is not considered a known tag.
     */
    @Test(timeout = 4000)
    public void tagDoesNotEqualPlainObjectAndIsNotKnown() throws Throwable {
        Tag tag = new Tag("", "");
        Object nonTag = new Object();

        assertFalse("A Tag must not equal a plain Object", tag.equals(nonTag));
        assertFalse("A newly created Tag should not be flagged as known", tag.isKnownTag());
    }
}
