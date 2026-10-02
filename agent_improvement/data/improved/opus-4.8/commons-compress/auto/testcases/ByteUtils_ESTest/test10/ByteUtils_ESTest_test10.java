package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test10 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteUtils#fromLittleEndian(ByteUtils.ByteSupplier, int)}
     * assembles five supplied bytes into a little-endian long.
     *
     * <p>Each of the five bytes has the value 5, so the resulting long is:
     * {@code 5*(2^0 + 2^8 + 2^16 + 2^24 + 2^32) = 21559051525}.</p>
     */
    @Test(timeout = 4000)
    public void readsFiveBytesAsLittleEndianLong() throws Throwable {
        final int byteValue = 5;
        final int byteCount = 5;

        // A supplier that returns the value 5 for every byte requested.
        ByteUtils.ByteSupplier constantByteSupplier =
                mock(ByteUtils.ByteSupplier.class, new ViolatedAssumptionAnswer());
        doReturn(byteValue, byteValue, byteValue, byteValue, byteValue)
                .when(constantByteSupplier).getAsByte();

        long result = ByteUtils.fromLittleEndian(constantByteSupplier, byteCount);

        assertEquals(21559051525L, result);
    }
}
