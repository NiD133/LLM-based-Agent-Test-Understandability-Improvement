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

    /**
     * Verifies that a user-created tag with an uppercase name ("A") is:
     * - normalized to its lowercase form ("a"),
     * - placed in the default HTML namespace,
     * - not self-closing, and
     * - not considered a known (pre-defined) tag.
     */
    @Test(timeout = 4000)
    public void test_uppercaseTagName_isNormalizedAndHasDefaultHtmlNamespaceAndIsNotSelfClosingOrKnown() throws Throwable {
        Tag uppercaseTag = new Tag("A");

        boolean isSelfClosing = uppercaseTag.isSelfClosing();

        assertEquals("a", uppercaseTag.normalName());
        assertFalse(isSelfClosing);
        assertEquals("http://www.w3.org/1999/xhtml", uppercaseTag.namespace());
        assertFalse(uppercaseTag.isKnownTag());
    }
}
