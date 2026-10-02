package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test22 extends Tag_ESTest_scaffolding {

    /**
     * A Tag created via the internal (tagName, normalName, namespace) constructor starts with no options set.
     * Verifies that formatAsBlock() returns false (InlineContainer flag is not set) and
     * isKnownTag() returns false (Known flag is not set) for such a freshly constructed tag.
     */
    @Test(timeout = 4000)
    public void test22_newTagWithNoOptionsIsNotInlineContainerAndNotKnown() throws Throwable {
        // Use the package-private 3-arg constructor: tagName, normalName, namespace — no option bits are set
        Tag customTag = new Tag("jh6", "jh6", "jh6");

        boolean isInlineContainer = customTag.formatAsBlock();

        assertFalse("A tag with no options set should not be an InlineContainer (formatAsBlock returns false)",
            isInlineContainer);
        assertFalse("A tag with no options set should not be a known tag",
            customTag.isKnownTag());
    }
}
