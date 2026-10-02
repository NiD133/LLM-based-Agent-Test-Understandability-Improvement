package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test45 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test45() throws Throwable {
        Tag tag0 = Tag.valueOf("Oc2:{");
        String string0 = tag0.getName();
        assertEquals("oc2:{", tag0.normalName());
        assertEquals("Oc2:{", string0);
        assertEquals("http://www.w3.org/1999/xhtml", tag0.namespace());
        assertFalse(tag0.isKnownTag());
        assertNotNull(string0);
    }
}
