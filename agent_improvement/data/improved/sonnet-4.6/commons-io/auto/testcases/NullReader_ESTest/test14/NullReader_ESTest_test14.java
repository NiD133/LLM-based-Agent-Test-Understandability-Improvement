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
public class NullReader_ESTest_test14 extends NullReader_ESTest_scaffolding {

    // NullReader accepts a negative size without throwing; markSupported() returns true by default.
    @Test(timeout = 4000)
    public void test_markSupportedIsTrueAndSizeIsPreservedForNegativeSize() throws Throwable {
        long negativeSize = -827L;
        NullReader reader = new NullReader(negativeSize);

        boolean isMarkSupported = reader.markSupported();
        assertTrue(isMarkSupported);
        assertEquals(negativeSize, reader.getSize());
    }
}
