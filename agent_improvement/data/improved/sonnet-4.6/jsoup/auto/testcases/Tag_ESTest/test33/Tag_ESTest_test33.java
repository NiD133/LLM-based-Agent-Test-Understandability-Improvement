package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test33 extends Tag_ESTest_scaffolding {

    // Arbitrary tag name/namespace used to exercise the package-private 3-arg constructor.
    // The value contains no ':', so prefix() will return "".
    private static final String ARBITRARY_NAME = "iP_$Km@lI4Ix)VZ";

    @Test(timeout = 4000)
    public void test33_newTagWithoutKnownOptionIsNotKnownTag() throws Throwable {
        // A Tag created directly (not via TagSet) has no options set, so isKnownTag() is false.
        Tag customTag = new Tag(ARBITRARY_NAME, ARBITRARY_NAME, ARBITRARY_NAME);
        customTag.prefix();
        assertFalse(customTag.isKnownTag());
    }
}
