package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test32 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test32() throws Throwable {
        Tag tag0 = new Tag("8:<KN-^%U");
        String string0 = tag0.localName();
        assertEquals("<KN-^%U", string0);
        assertEquals("http://www.w3.org/1999/xhtml", tag0.namespace());
        assertEquals("8:<kn-^%u", tag0.normalName());
        assertFalse(tag0.isKnownTag());
    }
}
