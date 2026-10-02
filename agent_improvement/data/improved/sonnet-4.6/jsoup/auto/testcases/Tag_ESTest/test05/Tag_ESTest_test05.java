package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test05 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that Tag.equals() returns false when compared to a non-Tag object,
     * and that a Tag constructed directly (not via TagSet) is not a known tag.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // A tag constructed directly with empty name and empty namespace
        Tag emptyTag = new Tag("", "");

        // Comparing a Tag to a plain Object should return false
        Object nonTagObject = new Object();
        boolean isEqualToNonTag = emptyTag.equals(nonTagObject);
        assertFalse(isEqualToNonTag);

        // A directly constructed Tag is not registered in any TagSet, so it is not known
        assertFalse(emptyTag.isKnownTag());
    }
}
