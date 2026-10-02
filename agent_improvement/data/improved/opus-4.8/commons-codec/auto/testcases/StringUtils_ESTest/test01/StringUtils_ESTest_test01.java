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
public class StringUtils_ESTest_test01 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that decoding a {@code null} byte array with the ISO-8859-1
     * charset returns {@code null} rather than throwing an exception.
     */
    @Test(timeout = 4000)
    public void newStringIso8859_1_withNullBytes_returnsNull() throws Throwable {
        String decoded = StringUtils.newStringIso8859_1((byte[]) null);

        assertNull(decoded);
    }
}
