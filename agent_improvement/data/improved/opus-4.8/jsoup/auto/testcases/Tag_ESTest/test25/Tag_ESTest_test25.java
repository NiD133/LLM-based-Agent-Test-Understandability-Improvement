package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test25 extends Tag_ESTest_scaffolding {

    /**
     * The "h6" heading tag is a known HTML tag, so resolving it yields a Tag
     * whose cached parser options include the bit value 128. Verifying that
     * hasParserOption(128) reports the option as set.
     */
    @Test(timeout = 4000)
    public void hasParserOption_forHeadingTag_returnsTrueForExpectedOptionBit() throws Throwable {
        Tag headingTag = Tag.valueOf("h6");

        boolean optionIsSet = headingTag.hasParserOption(128);

        assertTrue(optionIsSet);
    }
}
