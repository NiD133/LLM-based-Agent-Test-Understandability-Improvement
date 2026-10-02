package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test58 extends StringUtil_ESTest_scaffolding {

    /**
     * A negative padding width is invalid, so {@link StringUtil#padding(int)}
     * must reject it via Validate with an IllegalArgumentException ("width must be >= 0").
     */
    @Test(timeout = 4000)
    public void paddingWithNegativeWidthThrowsIllegalArgumentException() throws Throwable {
        try {
            StringUtil.padding(-2);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate.isTrue(width >= 0, "width must be >= 0") rejects the negative width
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
