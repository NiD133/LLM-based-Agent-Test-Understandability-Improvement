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
     * When the supplier immediately reports end-of-data (returns -1) before the
     * requested number of bytes could be read, {@code fromLittleEndian} must
     * fail with an IOException reporting a "Premature end of data" condition.
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_throwsWhenSupplierExhaustedBeforeReadingRequestedBytes() throws Throwable {
        // A supplier that always signals "no more bytes available".
        ByteUtils.ByteSupplier exhaustedSupplier =
                mock(ByteUtils.ByteSupplier.class, new ViolatedAssumptionAnswer());
        doReturn(-1).when(exhaustedSupplier).getAsByte();

        try {
            ByteUtils.fromLittleEndian(exhaustedSupplier, 1);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Thrown by ByteUtils with the message "Premature end of data".
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
