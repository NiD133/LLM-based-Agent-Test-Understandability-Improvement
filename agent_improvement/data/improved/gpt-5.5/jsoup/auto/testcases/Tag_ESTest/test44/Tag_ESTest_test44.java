package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test44 extends Tag_ESTest_scaffolding {

    private static final String MIXED_CASE_UNKNOWN_TAG_NAME = "MJ";
    private static final String CUSTOM_NAMESPACE = "MJ";
    private static final String NORMALIZED_TAG_NAME = "mj";

    @Test(timeout = 4000)
    public void test44() throws Throwable {
        ParseSettings preserveCaseSettings = ParseSettings.preserveCase;
        Tag unknownTag = Tag.valueOf(MIXED_CASE_UNKNOWN_TAG_NAME, CUSTOM_NAMESPACE, preserveCaseSettings);

        String actualNormalName = unknownTag.normalName();

        assertEquals(NORMALIZED_TAG_NAME, actualNormalName);
        assertEquals(CUSTOM_NAMESPACE, unknownTag.namespace());
        assertFalse(unknownTag.isKnownTag());
    }
}
