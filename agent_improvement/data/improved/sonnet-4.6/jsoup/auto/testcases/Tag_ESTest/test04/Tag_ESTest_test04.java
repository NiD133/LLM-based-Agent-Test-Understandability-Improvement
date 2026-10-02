package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test04 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that a Tag constructed directly (not registered in any TagSet)
     * is equal to itself, and is not considered a "known" tag.
     *
     * Tags are only marked as known when added to a TagSet or when set() is
     * called on them. A plain constructor call leaves the Known option unset.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Create a custom tag with an unconventional name and namespace ("]")
        // to ensure the tag is not matched by any built-in TagSet entry.
        Tag customTag = new Tag("]", "]");

        // A tag must be reflexively equal to itself (equals contract).
        boolean isSelfEqual = customTag.equals(customTag);
        assertTrue(isSelfEqual);

        // Tags created via the constructor are not registered in any TagSet,
        // so the Known option bit is never set — isKnownTag() must be false.
        assertFalse(customTag.isKnownTag());
    }
}
