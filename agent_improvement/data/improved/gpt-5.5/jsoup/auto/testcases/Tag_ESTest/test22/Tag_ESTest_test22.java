package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test22 extends Tag_ESTest_scaffolding {

    private static final String UNKNOWN_TAG_NAME = "jh6";
    private static final String UNKNOWN_NORMAL_NAME = "jh6";
    private static final String UNKNOWN_NAMESPACE = "jh6";

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Tag unknownTag = new Tag(UNKNOWN_TAG_NAME, UNKNOWN_NORMAL_NAME, UNKNOWN_NAMESPACE);

        boolean formatsAsBlock = unknownTag.formatAsBlock();

        assertFalse(formatsAsBlock);
        assertFalse(unknownTag.isKnownTag());
    }
}
