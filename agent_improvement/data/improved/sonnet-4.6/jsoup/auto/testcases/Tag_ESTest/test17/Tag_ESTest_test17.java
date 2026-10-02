package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test17 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Tag tag = new Tag("_F31ld-BAJ[");
        // A freshly created tag has no options set, so it is not a void (empty) tag
        assertFalse(tag.isEmpty());

        // Setting the Void option bit marks the tag as a void (empty) element
        tag.options = Tag.Void;
        assertTrue(tag.isEmpty());
    }
}
