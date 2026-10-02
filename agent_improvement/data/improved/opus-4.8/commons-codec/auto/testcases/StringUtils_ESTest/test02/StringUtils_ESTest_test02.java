package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test02 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#getBytesUnchecked(String, String)} returns
     * {@code null} when the input string is {@code null}, regardless of the charset
     * name supplied. The charset name is never consulted in this case.
     */
    @Test(timeout = 4000)
    public void getBytesUncheckedReturnsNullForNullString() throws Throwable {
        String nullInput = null;
        String charsetName = "&[:;4RVOrOFGnv4c^?";

        byte[] encodedBytes = StringUtils.getBytesUnchecked(nullInput, charsetName);

        assertNull(encodedBytes);
    }
}
