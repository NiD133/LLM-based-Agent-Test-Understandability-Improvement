package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test23 extends Tag_ESTest_scaffolding {

    /**
     * The HTML heading tag {@code <h6>} is a predefined block-level tag,
     * so {@link Tag#isBlock()} should report it as a block tag.
     */
    @Test(timeout = 4000)
    public void h6TagIsRecognizedAsBlock() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");

        boolean isBlock = h6Tag.isBlock();

        assertTrue("The <h6> heading tag is expected to be a block tag", isBlock);
    }
}
