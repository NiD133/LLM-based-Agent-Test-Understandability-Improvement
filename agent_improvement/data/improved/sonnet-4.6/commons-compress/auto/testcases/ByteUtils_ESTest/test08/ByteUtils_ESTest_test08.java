package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test08 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that fromLittleEndian(ByteSupplier, length) throws IOException
     * with "Premature end of data" when the supplier signals end-of-stream
     * by returning -1 before all requested bytes have been read.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Arrange: a supplier that immediately signals end-of-stream
        ByteUtils.ByteSupplier endOfStreamSupplier = mock(ByteUtils.ByteSupplier.class, new ViolatedAssumptionAnswer());
        doReturn((-1)).when(endOfStreamSupplier).getAsByte();

        // Act & Assert: reading 1 byte from an exhausted supplier must fail
        try {
            ByteUtils.fromLittleEndian(endOfStreamSupplier, 1);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
