package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test05 extends ByteUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05_fromLittleEndian_throwsIOException_whenStreamExhaustedBeforeRequestedByteCount() throws Throwable {
        // Stream has only 1 byte available (offset 8, count 1 in a 9-byte array),
        // but fromLittleEndian is asked to read 8 bytes — expect premature-end error.
        byte[] singleAvailableByte = new byte[9];
        ByteArrayInputStream streamWithOneByte = new ByteArrayInputStream(singleAvailableByte, 8, 1);

        try {
            ByteUtils.fromLittleEndian((InputStream) streamWithOneByte, 8);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
