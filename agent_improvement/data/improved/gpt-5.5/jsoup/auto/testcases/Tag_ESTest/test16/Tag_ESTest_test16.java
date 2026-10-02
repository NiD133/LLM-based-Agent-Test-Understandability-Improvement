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
        Tag uppercaseAnchorTag = new Tag("A");
        Tag tagWithInlineContainerAndSelfCloseOptions = uppercaseAnchorTag.set(28);

        boolean isSelfClosing = tagWithInlineContainerAndSelfCloseOptions.isSelfClosing();

        assertTrue(uppercaseAnchorTag.formatAsBlock());
        assertTrue(isSelfClosing);
    }
}
