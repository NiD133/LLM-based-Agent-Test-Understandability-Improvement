package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test09 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Tag tag0 = Tag.valueOf("bZJf");
        Tag tag1 = tag0.set(64);
        boolean boolean0 = tag1.preserveWhitespace();
        assertTrue(tag0.isKnownTag());
        assertTrue(boolean0);
    }
}
