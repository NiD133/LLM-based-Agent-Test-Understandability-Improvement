package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test04 extends WordUtils_ESTest_scaffolding {

    private static final String TEXT_WITH_NO_BREAKABLE_SPACES = ".*\b";
    private static final int NON_POSITIVE_WRAP_LENGTH = -1995;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        String wrappedText = WordUtils.wrap(TEXT_WITH_NO_BREAKABLE_SPACES, NON_POSITIVE_WRAP_LENGTH);

        assertEquals(TEXT_WITH_NO_BREAKABLE_SPACES, wrappedText);
    }
}
