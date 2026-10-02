package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test39 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test39() throws Throwable {
        Tag tag0 = new Tag("|=$:#:mei1", "|=$:#:mei1", "|=$:#:mei1");
        String string0 = tag0.toString();
        assertFalse(tag0.isKnownTag());
        assertNotNull(string0);
    }
}
