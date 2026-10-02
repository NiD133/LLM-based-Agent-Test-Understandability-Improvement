package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test24 extends StringUtils_ESTest_scaffolding {

    /**
     * Encoding a {@code null} string to UTF-16BE bytes should return {@code null}
     * rather than throwing, mirroring the null-in/null-out contract of
     * {@link StringUtils#getBytesUtf16Be(String)}.
     */
    @Test(timeout = 4000)
    public void getBytesUtf16Be_withNullString_returnsNull() throws Throwable {
        byte[] encodedBytes = StringUtils.getBytesUtf16Be((String) null);

        assertNull(encodedBytes);
    }
}
