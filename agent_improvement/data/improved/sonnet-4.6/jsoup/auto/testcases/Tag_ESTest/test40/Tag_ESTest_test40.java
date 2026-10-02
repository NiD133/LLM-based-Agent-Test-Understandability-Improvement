package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test40 extends Tag_ESTest_scaffolding {

    // Setting the Void option on a tag makes it both empty (isEmpty) and self-closing (isSelfClosing).
    @Test(timeout = 4000)
    public void test40() throws Throwable {
        Tag tag = new Tag("A");
        // set() mutates the tag in-place and returns the same instance
        Tag tagAfterSet = tag.set(Tag.Void);
        boolean isSelfClosing = tagAfterSet.isSelfClosing();
        assertTrue(tag.isEmpty());
        assertTrue(isSelfClosing);
    }
}
