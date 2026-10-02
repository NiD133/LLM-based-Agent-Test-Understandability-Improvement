package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test33 extends Tag_ESTest_scaffolding {

    private static final String UNKNOWN_TAG_NAME = "iP_$\u007FKm@lI4Ix)VZ";

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        Tag unknownTag = new Tag(UNKNOWN_TAG_NAME, UNKNOWN_TAG_NAME, UNKNOWN_TAG_NAME);

        unknownTag.prefix();

        assertFalse(unknownTag.isKnownTag());
    }
}
