package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test19 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // Create a custom (unknown) tag with an arbitrary name and namespace.
        // The tag name contains uppercase letters and trailing whitespace to verify
        // that normalName() lowercases and trims it, while namespace() is preserved verbatim.
        String arbitraryNameAndNamespace = "'k7*}2H9fkf;cif_ ";
        Tag customTag = new Tag(arbitraryNameAndNamespace, arbitraryNameAndNamespace);

        // Namespace is stored exactly as supplied
        assertEquals("'k7*}2H9fkf;cif_ ", customTag.namespace());

        // normalName is the lowercased (and trimmed) version of the tag name
        assertEquals("'k7*}2h9fkf;cif_", customTag.normalName());

        // A tag created directly via the constructor is not a known tag
        // (known tags come from a TagSet)
        assertFalse(customTag.isKnownTag());

        // Without the Block option set, the tag is inline by default
        assertTrue(customTag.isInline());
    }
}
