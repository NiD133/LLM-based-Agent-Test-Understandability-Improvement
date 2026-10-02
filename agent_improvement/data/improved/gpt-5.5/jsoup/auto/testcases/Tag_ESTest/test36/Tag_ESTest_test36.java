package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test36 extends Tag_ESTest_scaffolding {

    private static final String EMPTY_TAG_NAME = "";
    private static final String EMPTY_NAMESPACE = "";

    @Test(timeout = 4000)
    public void test36() throws Throwable {
        Tag emptyTag = new Tag(EMPTY_TAG_NAME, EMPTY_NAMESPACE);

        emptyTag.namespace();

        assertFalse(emptyTag.isKnownTag());
    }
}
