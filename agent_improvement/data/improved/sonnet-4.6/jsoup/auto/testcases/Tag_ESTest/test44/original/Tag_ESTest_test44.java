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

    @Test(timeout = 4000)
    public void test44() throws Throwable {
        ParseSettings parseSettings0 = ParseSettings.preserveCase;
        Tag tag0 = Tag.valueOf("MJ", "MJ", parseSettings0);
        String string0 = tag0.normalName();
        assertEquals("mj", string0);
        assertEquals("MJ", tag0.namespace());
        assertFalse(tag0.isKnownTag());
    }
}
