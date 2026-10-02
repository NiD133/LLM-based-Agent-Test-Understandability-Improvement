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
public class NullReader_ESTest_test11 extends NullReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_negativeSizeReader_markSupportedAndSizePreservedAfterSingletonClose() throws Throwable {
        long negativeSize = -827L;
        NullReader readerWithNegativeSize = new NullReader(negativeSize);

        // Close the shared singleton instance (accessed via static field INSTANCE)
        NullReader.INSTANCE.close();

        // The local reader instance should be unaffected: mark is supported by default
        assertTrue(readerWithNegativeSize.markSupported());
        // The size is stored as-is, even when negative
        assertEquals(negativeSize, readerWithNegativeSize.getSize());
    }
}
