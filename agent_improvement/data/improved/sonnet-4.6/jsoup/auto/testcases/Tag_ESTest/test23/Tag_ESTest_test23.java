package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test23 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that the HTML heading tag "h6" is recognized as a block-level element.
     * Heading tags (h1–h6) are block elements in HTML, meaning they occupy the full
     * width of their container and start on a new line.
     */
    @Test(timeout = 4000)
    public void test_h6HeadingTagIsBlock() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");

        boolean isBlock = h6Tag.isBlock();

        assertTrue("The <h6> heading tag should be a block-level element", isBlock);
    }
}
