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
     * A Tag created with empty name and namespace has no options set, so:
     * - textState() returns null (neither RcData nor Data flag is present)
     * - isKnownTag() returns false (the Known flag has not been set)
     */
    @Test(timeout = 4000)
    public void test_emptyNameTag_isNotKnownAndHasNoTextState() throws Throwable {
        Tag emptyTag = new Tag("", "");

        // textState() returns null because neither RcData nor Data option is set
        emptyTag.textState();

        // A newly constructed Tag without any options is not a known tag
        assertFalse(emptyTag.isKnownTag());
    }
}
