package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test08 extends Tag_ESTest_scaffolding {

    /**
     * A freshly constructed Tag has no options set, so it is neither
     * form-submittable nor recognised as a known tag.
     */
    @Test(timeout = 4000)
    public void newTagIsNotFormSubmittableAndNotKnown() throws Throwable {
        Tag tag = new Tag("", "");

        assertFalse("a bare tag should not be form-submittable", tag.isFormSubmittable());
        assertFalse("a bare tag should not be a known tag", tag.isKnownTag());
    }
}
