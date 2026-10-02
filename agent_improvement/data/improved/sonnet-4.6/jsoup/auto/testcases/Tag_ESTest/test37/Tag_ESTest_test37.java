package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test37 extends Tag_ESTest_scaffolding {

    // Verifies that an unknown custom tag created via valueOf() is not a known tag,
    // defaults to the HTML namespace, and normalizes its name to lowercase.
    @Test(timeout = 4000)
    public void test37_unknownCustomTagHasDefaultNamespaceAndLowercaseNormalName() throws Throwable {
        Tag unknownTag = Tag.valueOf("Oc2:{");

        unknownTag.hashCode();

        assertFalse(unknownTag.isKnownTag());
        assertEquals("http://www.w3.org/1999/xhtml", unknownTag.namespace());
        assertEquals("oc2:{", unknownTag.normalName());
    }
}
