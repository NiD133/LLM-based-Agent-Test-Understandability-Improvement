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

    /**
     * Verifies how an unknown, prefixed tag name is parsed by {@link Tag#valueOf(String)}.
     * The name "Oc2:{" carries a "Oc2" prefix (the part before the colon) and is
     * case-preserved as the tag name but lower-cased for the normal name.
     */
    @Test(timeout = 4000)
    public void valueOf_withPrefixedUnknownName_splitsPrefixAndNormalizesName() throws Throwable {
        Tag prefixedTag = Tag.valueOf("Oc2:{");

        assertEquals("prefix is the text before the colon", "Oc2", prefixedTag.prefix());
        assertEquals("normal name is the lower-cased tag name", "oc2:{", prefixedTag.normalName());
        assertEquals("unknown tags default to the XHTML namespace",
                "http://www.w3.org/1999/xhtml", prefixedTag.namespace());
        assertFalse("a freshly parsed, undefined tag is not a known tag", prefixedTag.isKnownTag());
    }
}
