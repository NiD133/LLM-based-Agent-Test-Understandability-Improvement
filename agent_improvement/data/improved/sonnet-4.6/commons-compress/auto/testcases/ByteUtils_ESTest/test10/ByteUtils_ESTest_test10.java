package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test10 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that fromLittleEndian correctly assembles a long from 5 bytes supplied
     * by a ByteSupplier, where each byte has the value 5.
     *
     * Five bytes of value 0x05 in little-endian order:
     *   0x05 | (0x05 << 8) | (0x05 << 16) | (0x05 << 24) | (0x05L << 32)
     *   = 5 + 1280 + 327680 + 83886080 + 21474836480 = 21559051525L
     */
    @Test(timeout = 4000)
    public void test10_fromLittleEndian_fiveBytesAllFive_returnsCorrectLong() throws Throwable {
        // Arrange: mock a ByteSupplier that returns the byte value 5 five consecutive times
        ByteUtils.ByteSupplier supplier = mock(ByteUtils.ByteSupplier.class, new ViolatedAssumptionAnswer());
        doReturn(5, 5, 5, 5, 5).when(supplier).getAsByte();

        // Act: read 5 little-endian bytes from the supplier into a long
        long result = ByteUtils.fromLittleEndian(supplier, 5);

        // Assert: 5 bytes each equal to 0x05 in little-endian encode to 21559051525L
        assertEquals(21559051525L, result);
    }
}
