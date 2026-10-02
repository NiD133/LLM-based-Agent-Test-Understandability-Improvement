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

    private static final String HEADING_LEVEL_SIX_TAG = "h6";
    private static final int RCDATA_PARSER_OPTION = 128;

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        Tag headingLevelSixTag = Tag.valueOf(HEADING_LEVEL_SIX_TAG);

        boolean hasRcDataParserOption = headingLevelSixTag.hasParserOption(RCDATA_PARSER_OPTION);

        assertTrue(hasRcDataParserOption);
    }
}
