package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test43 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that setSeenSelfClose() only records that a self-closing token was encountered
     * during parsing, without setting the SelfClose, Known, or any other option flags.
     * A tag whose only option is SeenSelfClose must not be treated as self-closing or known.
     */
    @Test(timeout = 4000)
    public void test43() throws Throwable {
        // Create a plain tag with no predefined options (empty name, empty namespace)
        Tag tag = new Tag("", "");

        // Mark that a self-close token was seen; this sets SeenSelfClose but NOT SelfClose or Known
        tag.setSeenSelfClose();

        // SeenSelfClose alone does not make the tag self-closing (SelfClose and Void are both unset)
        assertFalse(tag.isSelfClosing());

        // The tag has no options that would mark it as a known, block, whitespace-preserving,
        // or form-submittable tag, so all those properties must be false
        assertFalse(tag.isKnownTag());
        assertFalse(tag.formatAsBlock());
        assertFalse(tag.preserveWhitespace());
        assertFalse(tag.isFormSubmittable());

        // With no Block option set, the tag is inline
        assertTrue(tag.isInline());
    }
}
