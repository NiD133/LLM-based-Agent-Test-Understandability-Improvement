package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test24 extends Tag_ESTest_scaffolding {

    /**
     * A Tag created with empty name and empty namespace is neither a known tag
     * nor a block-level element, because no options (Known or Block) are set.
     */
    @Test(timeout = 4000)
    public void test_tagWithEmptyNameAndNamespace_isNeitherKnownNorBlock() throws Throwable {
        Tag emptyTag = new Tag("", "");

        boolean isBlock = emptyTag.isBlock();

        assertFalse("Tag with empty name should not be a known tag", emptyTag.isKnownTag());
        assertFalse("Tag with empty name should not be a block tag", isBlock);
    }
}
