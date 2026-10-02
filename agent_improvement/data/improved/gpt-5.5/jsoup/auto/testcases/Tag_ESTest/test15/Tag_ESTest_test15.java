package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test15 extends Tag_ESTest_scaffolding {

    private static final String UPPERCASE_ANCHOR_TAG = "A";
    private static final String NORMALIZED_ANCHOR_TAG = "a";
    private static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

    @Test(timeout = 4000)
    public void testUppercaseCustomTagDefaultsToLowercaseHtmlTag() throws Throwable {
        Tag uppercaseAnchorTag = new Tag(UPPERCASE_ANCHOR_TAG);
        boolean isSelfClosing = uppercaseAnchorTag.isSelfClosing();

        assertEquals(NORMALIZED_ANCHOR_TAG, uppercaseAnchorTag.normalName());
        assertFalse(isSelfClosing);
        assertEquals(HTML_NAMESPACE, uppercaseAnchorTag.namespace());
        assertFalse(uppercaseAnchorTag.isKnownTag());
    }
}
