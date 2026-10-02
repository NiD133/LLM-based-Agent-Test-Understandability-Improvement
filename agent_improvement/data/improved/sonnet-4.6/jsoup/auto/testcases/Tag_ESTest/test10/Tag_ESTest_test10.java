package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test10 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // A Tag created with empty tag name and empty namespace has no options set,
        // so it should neither preserve whitespace nor be considered a known tag.
        Tag emptyNameTag = new Tag("", "");

        boolean preservesWhitespace = emptyNameTag.preserveWhitespace();
        assertFalse(preservesWhitespace);
        assertFalse(emptyNameTag.isKnownTag());
    }
}
