package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test43 extends Tag_ESTest_scaffolding {

    /**
     * Marking a tag as having been seen self-closing only sets the internal
     * SeenSelfClose flag. It must not flip any of the other tag options:
     * the tag stays inline, non-self-closing, and unknown.
     */
    @Test(timeout = 4000)
    public void setSeenSelfCloseLeavesOtherTagOptionsUnchanged() throws Throwable {
        Tag tag = new Tag("", "");

        tag.setSeenSelfClose();

        assertFalse("seeing a self-close should not make the tag self-closing", tag.isSelfClosing());
        assertFalse("InlineContainer hint should remain unset", tag.formatAsBlock());
        assertFalse("whitespace preservation should remain off", tag.preserveWhitespace());
        assertTrue("a tag with no Block option is inline", tag.isInline());
        assertFalse("setSeenSelfClose does not mark the tag as known", tag.isKnownTag());
        assertFalse("tag should not be form submittable", tag.isFormSubmittable());
    }
}
