package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test43 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test43() throws Throwable {
        Tag tag0 = new Tag("", "");
        tag0.setSeenSelfClose();
        assertFalse(tag0.isSelfClosing());
        assertFalse(tag0.formatAsBlock());
        assertFalse(tag0.preserveWhitespace());
        assertTrue(tag0.isInline());
        assertFalse(tag0.isKnownTag());
        assertFalse(tag0.isFormSubmittable());
    }
}
