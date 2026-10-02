package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test25 extends Tag_ESTest_scaffolding {

    // HtmlTagOptions.Special = 1 << 7; heading elements like h6 are "special" in the HTML tree builder
    private static final int PARSER_OPTION_SPECIAL = 128;

    @Test(timeout = 4000)
    public void test_h6TagIsMarkedAsSpecialInHtmlParser() throws Throwable {
        Tag h6Tag = Tag.valueOf("h6");
        boolean isSpecial = h6Tag.hasParserOption(PARSER_OPTION_SPECIAL);
        assertTrue(isSpecial);
    }
}
