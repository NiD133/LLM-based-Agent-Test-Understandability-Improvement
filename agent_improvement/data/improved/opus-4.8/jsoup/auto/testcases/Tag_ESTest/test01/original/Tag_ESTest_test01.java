package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test01 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Tag tag0 = new Tag("t", "t");
        Tag tag1 = new Tag("t");
        boolean boolean0 = tag0.equals(tag1);
        assertFalse(boolean0);
        assertFalse(tag1.isKnownTag());
        assertFalse(tag1.equals((Object) tag0));
    }
}
