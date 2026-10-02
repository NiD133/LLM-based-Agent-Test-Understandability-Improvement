package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test45 extends StringUtil_ESTest_scaffolding {

    /**
     * padding(width) should return a string consisting of exactly {@code width} space characters.
     */
    @Test(timeout = 4000)
    public void paddingReturnsRequestedNumberOfSpaces() throws Throwable {
        int requestedWidth = 18;

        String actualPadding = StringUtil.padding(requestedWidth);

        String expectedPadding = "                  "; // 18 spaces
        assertEquals(expectedPadding, actualPadding);
    }
}
