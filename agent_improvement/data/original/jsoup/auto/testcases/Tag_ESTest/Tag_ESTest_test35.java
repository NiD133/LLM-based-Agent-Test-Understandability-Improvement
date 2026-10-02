package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test35 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        Tag tag0 = new Tag("org.jsoup.parser.Tag", "org.jsoup.parser.Tag");
        Tag tag1 = tag0.namespace("org.jsoup.parser.Tag");
        assertEquals("org.jsoup.parser.Tag", tag0.namespace());
        assertEquals("org.jsoup.parser.tag", tag1.normalName());
        assertFalse(tag1.isKnownTag());
    }
}
