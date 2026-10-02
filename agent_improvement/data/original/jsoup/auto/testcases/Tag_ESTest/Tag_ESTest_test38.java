package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test38 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        Tag tag0 = Tag.valueOf("I");
        Tag tag1 = new Tag("I");
        boolean boolean0 = tag0.equals(tag1);
        assertTrue(tag0.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", tag1.namespace());
        assertEquals("i", tag1.normalName());
        assertFalse(boolean0);
        assertFalse(tag1.equals((Object) tag0));
    }
}
