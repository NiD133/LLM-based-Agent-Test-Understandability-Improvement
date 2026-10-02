package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test29 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test29() throws Throwable {
        Tag originalUnknownTag = new Tag("", "");
        Tag clonedTag = originalUnknownTag.clone();

        clonedTag.options = 256; // Tag.Data
        clonedTag.textState();

        assertFalse(originalUnknownTag.isKnownTag());
    }
}
