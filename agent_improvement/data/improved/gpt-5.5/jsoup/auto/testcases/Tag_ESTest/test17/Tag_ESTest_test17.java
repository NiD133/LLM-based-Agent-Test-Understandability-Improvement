package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test17 extends Tag_ESTest_scaffolding {

    private static final String CUSTOM_TAG_NAME = "_F31ld-BAJ[";
    private static final int VOID_TAG_OPTION = 2;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Tag customTag = new Tag(CUSTOM_TAG_NAME);

        assertFalse(customTag.isEmpty());

        customTag.options = VOID_TAG_OPTION;
        boolean isEmptyAfterVoidOptionIsSet = customTag.isEmpty();

        assertTrue(isEmptyAfterVoidOptionIsSet);
    }
}
