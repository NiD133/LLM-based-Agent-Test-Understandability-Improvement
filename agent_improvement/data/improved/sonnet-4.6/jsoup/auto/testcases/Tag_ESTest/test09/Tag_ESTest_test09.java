package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test09 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Create an unknown custom tag; set() also marks it as Known (side-effect documented in Tag.set())
        Tag customTag = Tag.valueOf("bZJf");
        Tag tagWithWhitespace = customTag.set(Tag.PreserveWhitespace);
        boolean preservesWhitespace = tagWithWhitespace.preserveWhitespace();
        assertTrue(customTag.isKnownTag());
        assertTrue(preservesWhitespace);
    }
}
