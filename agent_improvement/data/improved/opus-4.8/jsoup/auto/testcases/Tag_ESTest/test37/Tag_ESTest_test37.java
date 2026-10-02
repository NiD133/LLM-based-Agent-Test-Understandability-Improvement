package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test37 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that resolving an unrecognized tag name produces a generic tag:
     * it is not flagged as a known tag, it defaults to the XHTML namespace, and
     * its normalized name is the lower-cased original name. Calling hashCode()
     * should not affect any of these properties.
     */
    @Test(timeout = 4000)
    public void unknownTagHasLowercasedNameAndDefaultNamespace() throws Throwable {
        Tag unknownTag = Tag.valueOf("Oc2:{");

        // hashCode is derived only from name and namespace; calling it must not mutate the tag.
        unknownTag.hashCode();

        assertFalse("An unrecognized tag should not be marked as known", unknownTag.isKnownTag());
        assertEquals("Default namespace should be XHTML",
            "http://www.w3.org/1999/xhtml", unknownTag.namespace());
        assertEquals("Normal name should be the lower-cased original tag name",
            "oc2:{", unknownTag.normalName());
    }
}
