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
public class ByteUtils_ESTest_test04 extends ByteUtils_ESTest_scaffolding {

    /**
     * When toLittleEndian is called with a negative length (cast from byte -1 to int gives -1),
     * the internal loop does not execute, so the target buffer remains entirely unchanged.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // A negative length is produced by casting byte -1 to int (both evaluate to -1).
        final int negativeOffset = (int) (byte) (-1); // -1
        final int negativeLength = (int) (byte) (-1); // -1

        byte[] buffer = new byte[5];
        byte[] expectedUnchangedBuffer = new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0 };

        // The loop inside toLittleEndian iterates while i < length; with length == -1 the
        // loop body never runs, so no bytes are written and the buffer stays all-zero.
        ByteUtils.toLittleEndian(buffer, 4466L, negativeOffset, negativeLength);

        assertArrayEquals(expectedUnchangedBuffer, buffer);
    }
}
