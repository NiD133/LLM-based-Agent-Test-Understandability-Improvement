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

    /**
     * Verifies that an unknown tag created via valueOf() with a mixed-case name:
     * - preserves the original case in getName()
     * - stores a lowercased version in normalName()
     * - defaults to the HTML namespace
     * - is not considered a known (pre-defined) tag
     */
    @Test(timeout = 4000)
    public void test45_unknownTagPreservesCaseAndUsesHtmlNamespace() throws Throwable {
        // "Oc2:{" is not a standard HTML tag, so it will be treated as unknown
        Tag unknownTag = Tag.valueOf("Oc2:{");

        // getName() should return the original case-preserved tag name
        String originalName = unknownTag.getName();
        assertEquals("Oc2:{", originalName);
        assertNotNull(originalName);

        // normalName() should return the lowercased version
        assertEquals("oc2:{", unknownTag.normalName());

        // Unknown tags still get placed in the default HTML namespace
        assertEquals("http://www.w3.org/1999/xhtml", unknownTag.namespace());

        // Since "Oc2:{" is not a pre-defined tag, it must not be a known tag
        assertFalse(unknownTag.isKnownTag());
    }
}
