package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test08 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Tag emptyUnknownTag = new Tag("", "");

        boolean isFormSubmittable = emptyUnknownTag.isFormSubmittable();

        assertFalse(emptyUnknownTag.isKnownTag());
        assertFalse(isFormSubmittable);
    }
}
