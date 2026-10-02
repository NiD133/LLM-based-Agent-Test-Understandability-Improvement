package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test34 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test34() throws Throwable {
        final String mixedCasePrefixedTagName = "Oc2:{";
        Tag parsedTag = Tag.valueOf(mixedCasePrefixedTagName);

        String parsedPrefix = parsedTag.prefix();

        assertFalse(parsedTag.isKnownTag());
        assertEquals("Oc2", parsedPrefix);
        assertEquals("oc2:{", parsedTag.normalName());
        assertEquals("http://www.w3.org/1999/xhtml", parsedTag.namespace());
    }
}
