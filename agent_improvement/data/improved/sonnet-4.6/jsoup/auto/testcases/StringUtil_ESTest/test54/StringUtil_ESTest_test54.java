package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test54 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void normaliseWhitespace_collapsesMultipleTrailingSpacesToOne() throws Throwable {
        // Input has a question mark followed by four spaces; all trailing whitespace should collapse to a single space.
        String normalised = StringUtil.normaliseWhitespace("?    ");
        assertEquals("? ", normalised);
    }
}
