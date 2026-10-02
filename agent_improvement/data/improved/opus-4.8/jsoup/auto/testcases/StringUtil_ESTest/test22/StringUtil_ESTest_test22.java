package org.jsoup.internal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test22 extends StringUtil_ESTest_scaffolding {

    /**
     * When the input is entirely whitespace and leading whitespace is stripped,
     * nothing is ever appended to the accumulator. The all-spaces input is
     * therefore fully consumed without the {@code null} StringBuilder being
     * touched, so no NullPointerException is thrown.
     */
    @Test(timeout = 4000)
    public void appendNormalisedWhitespace_stripsAllSpaces_withoutUsingNullAccumulator() throws Throwable {
        StringBuilder nullAccumulator = null;
        String allSpaces = "          ";
        boolean stripLeading = true;

        StringUtil.appendNormalisedWhitespace(nullAccumulator, allSpaces, stripLeading);
    }
}
