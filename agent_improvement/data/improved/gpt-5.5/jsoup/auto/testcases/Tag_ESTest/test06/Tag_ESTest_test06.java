package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test06 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final String tagName = "Oc2";
        final String namespace = "Oc2";
        final int rcDataOptionMask = 512;

        ParseSettings htmlDefaultSettings = ParseSettings.htmlDefault;
        Tag tag = Tag.valueOf(tagName, namespace, htmlDefaultSettings);

        assertFalse(tag.isKnownTag());

        Tag.RcData = rcDataOptionMask;
        tag.options = rcDataOptionMask;
        tag.textState();

        assertEquals(namespace, tag.namespace());
    }
}
