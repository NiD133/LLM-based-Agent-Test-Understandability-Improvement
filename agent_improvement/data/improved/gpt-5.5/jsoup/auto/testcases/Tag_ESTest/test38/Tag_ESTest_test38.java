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
        Tag registeredUppercaseItalicTag = Tag.valueOf("I");
        Tag constructedUppercaseItalicTag = new Tag("I");

        boolean registeredEqualsConstructed = registeredUppercaseItalicTag.equals(constructedUppercaseItalicTag);

        assertTrue(registeredUppercaseItalicTag.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", constructedUppercaseItalicTag.namespace());
        assertEquals("i", constructedUppercaseItalicTag.normalName());
        assertFalse(registeredEqualsConstructed);
        assertFalse(constructedUppercaseItalicTag.equals((Object) registeredUppercaseItalicTag));
    }
}
