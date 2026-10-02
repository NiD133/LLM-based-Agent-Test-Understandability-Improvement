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

    private static final String ORIGINAL_TAG_NAME = "'k7*}2H9fkf;cif_ ";
    private static final String ORIGINAL_NAMESPACE = "'k7*}2H9fkf;cif_ ";
    private static final String NORMALIZED_TAG_NAME = "'k7*}2h9fkf;cif_";

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        Tag tag = new Tag(ORIGINAL_TAG_NAME, ORIGINAL_NAMESPACE);

        boolean isInline = tag.isInline();

        assertEquals(ORIGINAL_NAMESPACE, tag.namespace());
        assertEquals(NORMALIZED_TAG_NAME, tag.normalName());
        assertFalse(tag.isKnownTag());
        assertTrue(isInline);
    }
}
