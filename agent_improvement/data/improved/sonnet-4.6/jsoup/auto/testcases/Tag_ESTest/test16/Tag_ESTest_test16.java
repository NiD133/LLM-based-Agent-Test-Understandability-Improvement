package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test16 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Tag tag = new Tag("A");
        // Setting Block | InlineContainer | SelfClose (4 | 8 | 16 = 28) marks the tag
        // as a block-formatted, self-closing element.
        Tag configuredTag = tag.set(Tag.Block | Tag.InlineContainer | Tag.SelfClose);
        boolean isSelfClosing = configuredTag.isSelfClosing();
        assertTrue(tag.formatAsBlock());
        assertTrue(isSelfClosing);
    }
}
