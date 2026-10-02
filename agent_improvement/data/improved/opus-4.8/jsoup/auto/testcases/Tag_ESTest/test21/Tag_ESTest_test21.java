package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test21 extends Tag_ESTest_scaffolding {

    /**
     * The predefined heading tag {@code <h6>} carries the InlineContainer
     * pretty-print hint, so {@link Tag#formatAsBlock()} reports true.
     */
    @Test(timeout = 4000)
    public void h6TagFormatsAsBlock() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");

        boolean formatsAsBlock = h6Tag.formatAsBlock();

        assertTrue(formatsAsBlock);
    }
}
