package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test02 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Tag tag0 = Tag.valueOf("html");
        Tag tag1 = new Tag("7E$B_fi2(/F6\"]&R$!6");
        boolean boolean0 = tag0.equals(tag1);
        assertFalse(boolean0);
        assertTrue(tag0.isKnownTag());
        assertEquals("7e$b_fi2(/f6\"]&r$!6", tag1.normalName());
        assertFalse(tag1.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", tag1.namespace());
    }
}
