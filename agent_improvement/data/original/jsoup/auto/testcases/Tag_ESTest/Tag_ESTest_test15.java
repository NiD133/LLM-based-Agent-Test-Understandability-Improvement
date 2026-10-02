package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test15 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Tag tag0 = new Tag("A");
        boolean boolean0 = tag0.isSelfClosing();
        assertEquals("a", tag0.normalName());
        assertFalse(boolean0);
        assertEquals("http://www.w3.org/1999/xhtml", tag0.namespace());
        assertFalse(tag0.isKnownTag());
    }
}
