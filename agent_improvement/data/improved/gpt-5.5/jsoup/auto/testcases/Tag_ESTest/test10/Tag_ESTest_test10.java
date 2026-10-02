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
        Tag emptyTag = new Tag("", "");

        boolean preservesWhitespaceByDefault = emptyTag.preserveWhitespace();

        assertFalse("A newly constructed empty tag should not preserve whitespace.", preservesWhitespaceByDefault);
        assertFalse("A tag constructed directly with no options should not be known.", emptyTag.isKnownTag());
    }
}
