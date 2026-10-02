package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test05 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Tag tag0 = new Tag("", "");
        Object object0 = new Object();
        boolean boolean0 = tag0.equals(object0);
        assertFalse(boolean0);
        assertFalse(tag0.isKnownTag());
    }
}
