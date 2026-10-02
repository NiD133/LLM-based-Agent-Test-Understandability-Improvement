package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test20 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that reading a single byte from a NullInputStream created with a negative size
     * succeeds and returns the default byte value (0).
     *
     * A negative size means position (0) never equals size (-30), so read() does not hit EOF.
     * Instead it advances the position by one and delegates to processByte(), which returns 0.
     */
    @Test(timeout = 4000)
    public void test_readSingleByte_withNegativeSize_returnsZeroAndAdvancesPosition() throws Throwable {
        // A stream with negative size: position starts at 0, which is != -30, so read() won't signal EOF
        NullInputStream streamWithNegativeSize = new NullInputStream(-30L);

        int byteRead = streamWithNegativeSize.read();

        // processByte() always returns 0 in the base implementation
        assertEquals(0, byteRead);
        // Position advances by one after a successful read
        assertEquals(1L, streamWithNegativeSize.getPosition());
    }
}
