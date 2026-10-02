package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test20 extends Tag_ESTest_scaffolding {

    /**
     * The "h6" heading tag is a block-level tag, so it must not be reported as inline.
     */
    @Test(timeout = 4000)
    public void h6TagIsNotInline() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");

        boolean inline = h6Tag.isInline();

        assertFalse("h6 is a block tag and should not be inline", inline);
    }
}
