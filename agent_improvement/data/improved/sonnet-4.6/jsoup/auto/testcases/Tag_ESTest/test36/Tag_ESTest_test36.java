package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test36 extends Tag_ESTest_scaffolding {

    // A Tag constructed directly (not registered via TagSet) should not be considered a known tag.
    @Test(timeout = 4000)
    public void test36() throws Throwable {
        Tag emptyNameTag = new Tag("", "");
        emptyNameTag.namespace(); // verify namespace() does not throw for empty-string inputs
        assertFalse(emptyNameTag.isKnownTag());
    }
}
