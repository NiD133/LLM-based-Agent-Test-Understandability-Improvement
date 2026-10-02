package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test09 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that parseFromCentralDirectoryData throws ArrayIndexOutOfBoundsException
     * when the flags byte has CREATE_TIME_BIT (0x04) set but the buffer does not contain
     * enough bytes after the flags byte to read the required 4-byte timestamp value.
     *
     * Buffer layout: [0x00, 0x00, 0x04]  (length 3)
     *   - offset 2 points to the flags byte (value 0x04 = CREATE_TIME_BIT set)
     *   - after consuming the flags byte the read position moves to index 3,
     *     which is past the end of the 3-element array, causing ByteUtils to
     *     throw ArrayIndexOutOfBoundsException when it tries to read 4 bytes.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Three-byte buffer where only index 2 (the flags byte) is populated.
        // Setting it to CREATE_TIME_BIT (4) tells the parser that a 4-byte
        // create-timestamp follows, but no such bytes exist in the buffer.
        byte[] buffer = new byte[3];
        buffer[2] = X5455_ExtendedTimestamp.CREATE_TIME_BIT; // 0x04

        // Parse starting at offset 2 with a large declared length (25461),
        // so the length check inside the parser does not guard against the
        // missing timestamp bytes — only the physical array boundary does.
        int offset = 2;
        int declaredLength = 25461;

        // Undeclared exception!
        try {
            extendedTimestamp.parseFromCentralDirectoryData(buffer, offset, declaredLength);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // ByteUtils tries to read from index 3 of a length-3 array
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
