package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test21 extends Tag_ESTest_scaffolding {

    // h6 is a heading tag: a block element whose inline children should stay inline,
    // so formatAsBlock() (which checks the InlineContainer option) must return true.
    @Test(timeout = 4000)
    public void test21_h6HeadingTagFormatsAsBlock() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");
        boolean formatsAsBlock = h6Tag.formatAsBlock();
        assertTrue("h6 is a block-level heading whose inline children stay inline, so formatAsBlock() should return true", formatsAsBlock);
    }
}
