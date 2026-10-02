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
        Tag tagWithPrefix = new Tag("8:<KN-^%U");

        String localName = tagWithPrefix.localName();

        assertEquals("<KN-^%U", localName);
        assertEquals("http://www.w3.org/1999/xhtml", tagWithPrefix.namespace());
        assertEquals("8:<kn-^%u", tagWithPrefix.normalName());
        assertFalse(tagWithPrefix.isKnownTag());
    }
}
