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
        Tag unknownTag = Tag.valueOf("3urw0GS^ `H");

        // Clearing the Known bit keeps the tag unknown while returning the same Tag instance.
        Tag tagAfterClearingKnownBit = unknownTag.clear(1);

        assertEquals("3urw0gs^ `h", tagAfterClearingKnownBit.normalName());
        assertFalse(tagAfterClearingKnownBit.isSelfClosing());
        assertFalse(tagAfterClearingKnownBit.preserveWhitespace());
        assertFalse(unknownTag.isKnownTag());
        assertFalse(tagAfterClearingKnownBit.formatAsBlock());
        assertEquals("http://www.w3.org/1999/xhtml", tagAfterClearingKnownBit.namespace());
        assertTrue(tagAfterClearingKnownBit.isInline());
        assertFalse(tagAfterClearingKnownBit.isFormSubmittable());
    }
}
