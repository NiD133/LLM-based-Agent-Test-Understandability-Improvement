package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test04 extends TagSet_ESTest_scaffolding {

    // The same normal (lower-cased) name is reused for two different raw tag names.
    private static final String SHARED_NORMAL_NAME = "~m&2\"v*M>Y$C[<";
    private static final String EMPTY_NAMESPACE = "";

    /**
     * valueOf(tagName, normalName, namespace, preserveTagCase) creates and registers a new Tag
     * when none is already present, deriving the Tag's properties from the supplied arguments.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        TagSet tagSet = new TagSet();

        // First, unknown tag: not case-preserving, so its public name falls back to the normal name.
        Tag firstTag = tagSet.valueOf("v;;K-", SHARED_NORMAL_NAME, EMPTY_NAMESPACE, false);
        assertEquals(SHARED_NORMAL_NAME, firstTag.localName());

        // Second, distinct tag name sharing the same normal name, this time preserving case.
        Tag secondTag = tagSet.valueOf("J", SHARED_NORMAL_NAME, EMPTY_NAMESPACE, true);
        assertEquals(EMPTY_NAMESPACE, secondTag.namespace());
        assertEquals(SHARED_NORMAL_NAME, secondTag.normalName());
        assertEquals("J", secondTag.name());
    }
}
