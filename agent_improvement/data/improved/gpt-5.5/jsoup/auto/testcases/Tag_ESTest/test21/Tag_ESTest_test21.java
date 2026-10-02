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

    private static final String HIGHEST_LEVEL_HEADING_TAG = "h6";

    @Test(timeout = 4000)
    public void h6FormatsAsBlock() throws Throwable {
        Tag headingTag = Tag.valueOf(HIGHEST_LEVEL_HEADING_TAG);

        boolean formatsAsBlock = headingTag.formatAsBlock();

        assertTrue("h6 should format as a block-level heading tag", formatsAsBlock);
    }
}
