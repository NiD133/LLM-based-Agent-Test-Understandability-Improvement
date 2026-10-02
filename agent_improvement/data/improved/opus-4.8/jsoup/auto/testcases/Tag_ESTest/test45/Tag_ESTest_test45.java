package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test45 extends Tag_ESTest_scaffolding {

    /**
     * Resolving an unrecognised, mixed-case tag name should preserve the original
     * spelling as the tag name while exposing a lower-cased normal name. Because the
     * name is not a predefined HTML tag, the resulting Tag is generic (not "known"),
     * but it still defaults to the HTML namespace.
     */
    @Test(timeout = 4000)
    public void unknownMixedCaseTag_preservesNameButLowercasesNormalName() throws Throwable {
        Tag tag = Tag.valueOf("Oc2:{");

        String name = tag.getName();

        // Original spelling is retained for the name, lower-cased for the normal name.
        assertEquals("Oc2:{", name);
        assertNotNull(name);
        assertEquals("oc2:{", tag.normalName());

        // Defaults for an auto-created, unrecognised tag.
        assertEquals("http://www.w3.org/1999/xhtml", tag.namespace());
        assertFalse(tag.isKnownTag());
    }
}
