package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test30 extends Tag_ESTest_scaffolding {

    /**
     * A Tag created directly via the constructor (not registered in a TagSet and
     * without any option set) should not be treated as a known tag, and its
     * textState() should return null because neither RcData nor Data options are set.
     */
    @Test(timeout = 4000)
    public void test_directlyConstructedTagWithEmptyNameIsUnknownAndHasNoTextState() throws Throwable {
        // Create a tag with empty name and empty namespace directly (not via TagSet)
        Tag emptyTag = new Tag("", "");

        // textState() returns null when neither RcData nor Data option is set
        emptyTag.textState();

        // A tag created directly without any options set is not considered a known tag
        assertFalse(emptyTag.isKnownTag());
    }
}
