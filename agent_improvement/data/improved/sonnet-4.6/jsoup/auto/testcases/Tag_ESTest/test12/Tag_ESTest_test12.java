package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test12 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void isKnownTag_returnsfalse_forUnknownTagNameWithSpecialCharacters() throws Throwable {
        boolean isKnown = Tag.isKnownTag("iLUa^");
        assertFalse(isKnown);
    }
}
