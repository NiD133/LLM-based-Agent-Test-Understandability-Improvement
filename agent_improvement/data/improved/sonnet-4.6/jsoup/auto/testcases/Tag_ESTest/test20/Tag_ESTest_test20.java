package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test20 extends Tag_ESTest_scaffolding {

    // h6 is a heading tag, which is a block-level element, so isInline() must return false
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");
        boolean isInline = h6Tag.isInline();
        assertFalse("h6 is a block-level heading tag and should not be inline", isInline);
    }
}
