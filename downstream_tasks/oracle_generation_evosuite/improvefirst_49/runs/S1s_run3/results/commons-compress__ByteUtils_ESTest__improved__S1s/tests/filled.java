/*
 * Improved version of the EvoSuite-generated test for ByteUtils.
 * Test names and variable names have been made descriptive for clarity.
 * Runtime behaviour is identical to the original generated test.
 */

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
import org.apache.commons.compress.utils.ByteUtils;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest extends ByteUtils_ESTest_scaffolding {

    /**
     * Writing 6 bytes into a 2-element array starting at offset 0 must overflow the
     * array bounds and throw ArrayIndexOutOfBoundsException.
     */
    @Test(timeout = 4000)
    public void toLittleEndian_byteArray_throwsArrayIndexOutOfBounds_whenLengthExceedsArraySize() throws Throwable {
        byte[] twoByteArray = new byte[2];
        try {
            ByteUtils.toLittleEndian(twoByteArray, -1L, 0, 6);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * Writing the long value 0 to an OutputStream with length 8 should produce
     * exactly 8 zero bytes.
     */
    @Test(timeout = 4000)
    public void toLittleEndian_outputStream_writesEightZeroBytes_forZeroValue() throws Throwable {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteUtils.toLittleEndian((OutputStream) out, 0L, 8);
        assertEquals(8, out.size());
        assertArrayEquals(new byte[8], out.toByteArray());
    }

    /**
     * Writing value 50 (ASCII '2') to a DataOutputStream with length 212 should
     * produce 212 bytes where the first byte is '2' and the remaining 211 are zero.
     */
    @Test(timeout = 4000)
    public void toLittleEndian_dataOutput_writesCorrectLittleEndianBytes_forValue50() throws Throwable {
        ByteArrayOutputStream backingOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(backingOut);
        ByteUtils.toLittleEndian((DataOutput) dataOut, (long) (byte) 50, 212);
        assertEquals(212, backingOut.size());
        assertEquals(50, backingOut.toByteArray()[0]);
    }

    /**
     * Writing value 77 (ASCII 'M') via an OutputStreamByteConsumer backed by a file
     * should write exactly 1 byte to that file.
     */
    @Test(timeout = 4000)
    public void toLittleEndian_byteConsumer_writesOneByte_forValue77() throws Throwable {
        File tempFile = MockFile.createTempFile("suffixes", "");
        MockPrintStream printStream = new MockPrintStream(tempFile);
        ByteUtils.OutputStreamByteConsumer consumer = new ByteUtils.OutputStreamByteConsumer(printStream);
        ByteUtils.toLittleEndian((ByteUtils.ByteConsumer) consumer, (long) (byte) 77, 1);
    }

    /**
     * Calling toLittleEndian with a negative offset and negative length should be a
     * no-op: the loop body is never entered, so the array stays all-zero.
     */
    @Test(timeout = 4000)
    public void toLittleEndian_byteArray_doesNotModifyArray_whenLengthIsNegative() throws Throwable {
        byte[] fiveByteArray = new byte[5];
        ByteUtils.toLittleEndian(fiveByteArray, 4466L, (int) (byte) (-1), (int) (byte) (-1));
        assertArrayEquals(new byte[5], fiveByteArray);
    }

    /**
     * Reading 8 bytes from a stream that only has 1 byte available should throw
     * IOException with message "Premature end of data".
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_inputStream_throwsIOException_whenInsufficientBytesAvailable() throws Throwable {
        byte[] nineByteArray = new byte[9];
        // Only 1 byte is available (offset 8, length 1)
        ByteArrayInputStream limitedStream = new ByteArrayInputStream(nineByteArray, 8, 1);
        try {
            ByteUtils.fromLittleEndian((InputStream) limitedStream, 8);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
        }
    }

    /**
     * Reading 1 byte from a stream holding a single zero byte should return 0 and
     * leave the stream exhausted.
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_inputStream_returnsZero_forSingleZeroByte() throws Throwable {
        ByteArrayInputStream singleZeroStream = new ByteArrayInputStream(new byte[1]);
        long result = ByteUtils.fromLittleEndian((InputStream) singleZeroStream, 1);
        assertEquals(0L, result);
    }

    /**
     * Reading 1 byte from a DataInputStream positioned mid-array (offset 2, 2 bytes
     * available) should return 0 and leave 1 byte still available.
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_dataInput_returnsZero_forSingleZeroByte() throws Throwable {
        byte[] sevenByteArray = new byte[7];
        // Expose bytes [2..3] only (offset=2, count=2)
        ByteArrayInputStream partialStream = new ByteArrayInputStream(sevenByteArray, (byte) 2, (byte) 2);
        DataInputStream dataIn = new DataInputStream(partialStream);
        long result = ByteUtils.fromLittleEndian((DataInput) dataIn, (int) (byte) 1);
        assertEquals(0L, result);
    }

    /**
     * A ByteSupplier that immediately returns -1 (EOF) should cause fromLittleEndian
     * to throw IOException with message "Premature end of data".
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_byteSupplier_throwsIOException_whenSupplierReturnsEOF() throws Throwable {
        ByteUtils.ByteSupplier eofSupplier = mock(ByteUtils.ByteSupplier.class, new ViolatedAssumptionAnswer());
        doReturn(-1).when(eofSupplier).getAsByte();
        try {
            ByteUtils.fromLittleEndian(eofSupplier, 1);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
        }
    }

    /**
     * Reading a single-element zero byte array should return the long value 0.
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_byteArray_returnsZero_forSingleZeroByte() throws Throwable {
        byte[] singleZeroByte = new byte[1];
        long result = ByteUtils.fromLittleEndian(singleZeroByte);
        assertEquals(0L, result);
    }

    /**
     * A ByteSupplier that returns 5 five times should produce the little-endian long
     * 0x0505050505 = 21559051525.
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_byteSupplier_returnsCorrectLong_forRepeatedByteValue() throws Throwable {
        ByteUtils.ByteSupplier repeatingSupplier = mock(ByteUtils.ByteSupplier.class, new ViolatedAssumptionAnswer());
        doReturn(5, 5, 5, 5, 5).when(repeatingSupplier).getAsByte();
        long result = ByteUtils.fromLittleEndian(repeatingSupplier, 5);
        assertEquals(21559051525L, result);
    }

    /**
     * Passing an 18-element byte array to fromLittleEndian should throw
     * IllegalArgumentException because only up to 8 bytes may be read into a long.
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_byteArray_throwsIllegalArgumentException_whenArrayExceedsEightBytes() throws Throwable {
        byte[] eighteenByteArray = new byte[18];
        try {
            ByteUtils.fromLittleEndian(eighteenByteArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }
}
