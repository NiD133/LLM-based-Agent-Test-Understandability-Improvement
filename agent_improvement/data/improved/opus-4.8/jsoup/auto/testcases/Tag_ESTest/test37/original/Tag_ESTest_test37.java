package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test37 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test37() throws Throwable {
        Tag tag0 = Tag.valueOf("Oc2:{");
        tag0.hashCode();
        assertFalse(tag0.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", tag0.namespace());
        assertEquals("oc2:{", tag0.normalName());
    }
}
