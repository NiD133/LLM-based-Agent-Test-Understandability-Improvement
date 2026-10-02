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
public class NullInputStream_ESTest_test16 extends NullInputStream_ESTest_scaffolding {

    /**
     * The single-argument constructor should record the emulated size verbatim
     * and, per its contract, enable mark support by default.
     */
    @Test(timeout = 4000)
    public void sizeConstructorRecordsSizeAndEnablesMarkSupport() throws Throwable {
        final long emulatedSize = 5480L;
        NullInputStream stream = new NullInputStream(emulatedSize);

        assertEquals("getSize() should return the emulated size passed to the constructor",
                emulatedSize, stream.getSize());
        assertTrue("the single-argument constructor enables mark support by default",
                stream.markSupported());
    }
}
