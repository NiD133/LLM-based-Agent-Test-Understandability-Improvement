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
        Tag unknownMixedCaseTag = Tag.valueOf("Oc2:{");
        String originalTagName = unknownMixedCaseTag.getName();

        assertEquals("oc2:{", unknownMixedCaseTag.normalName());
        assertEquals("Oc2:{", originalTagName);
        assertEquals("http://www.w3.org/1999/xhtml", unknownMixedCaseTag.namespace());
        assertFalse(unknownMixedCaseTag.isKnownTag());
        assertNotNull(originalTagName);
    }
}
