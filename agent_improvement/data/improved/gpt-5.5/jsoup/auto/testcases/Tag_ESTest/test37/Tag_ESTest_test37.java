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

    private static final String MIXED_CASE_UNKNOWN_TAG_NAME = "Oc2:{";
    private static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
    private static final String NORMALIZED_TAG_NAME = "oc2:{";

    @Test(timeout = 4000)
    public void test37() throws Throwable {
        Tag unknownTag = Tag.valueOf(MIXED_CASE_UNKNOWN_TAG_NAME);

        unknownTag.hashCode();

        assertFalse(unknownTag.isKnownTag());
        assertEquals(HTML_NAMESPACE, unknownTag.namespace());
        assertEquals(NORMALIZED_TAG_NAME, unknownTag.normalName());
    }
}
