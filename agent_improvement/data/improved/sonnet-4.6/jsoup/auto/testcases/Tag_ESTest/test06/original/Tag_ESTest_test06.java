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
        ParseSettings parseSettings0 = ParseSettings.htmlDefault;
        Tag tag0 = Tag.valueOf("Oc2", "Oc2", parseSettings0);
        assertFalse(tag0.isKnownTag());
        Tag.RcData = 512;
        tag0.options = 512;
        tag0.textState();
        assertEquals("Oc2", tag0.namespace());
    }
}
