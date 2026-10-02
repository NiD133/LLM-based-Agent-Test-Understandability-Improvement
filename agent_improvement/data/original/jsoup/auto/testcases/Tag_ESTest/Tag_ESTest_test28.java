package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test28 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Tag tag0 = Tag.valueOf("3urw0GS^ `H");
        Tag tag1 = tag0.clear(1);
        assertEquals("3urw0gs^ `h", tag1.normalName());
        assertFalse(tag1.isSelfClosing());
        assertFalse(tag1.preserveWhitespace());
        assertFalse(tag0.isKnownTag());
        assertFalse(tag1.formatAsBlock());
        assertEquals("http://www.w3.org/1999/xhtml", tag1.namespace());
        assertTrue(tag1.isInline());
        assertFalse(tag1.isFormSubmittable());
    }
}
