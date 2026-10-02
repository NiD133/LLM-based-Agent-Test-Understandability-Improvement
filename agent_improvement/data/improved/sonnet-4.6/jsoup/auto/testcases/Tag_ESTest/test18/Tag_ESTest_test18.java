package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test18 extends Tag_ESTest_scaffolding {

    /**
     * A Tag constructed directly (not via TagSet) has no options set, so
     * isEmpty() (which checks the Void option) returns false, and
     * isKnownTag() (which checks the Known option) also returns false.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Tag emptyNameTag = new Tag("", "");

        assertFalse("A freshly constructed Tag should not be a void/empty tag", emptyNameTag.isEmpty());
        assertFalse("A freshly constructed Tag should not be a known tag", emptyNameTag.isKnownTag());
    }
}
