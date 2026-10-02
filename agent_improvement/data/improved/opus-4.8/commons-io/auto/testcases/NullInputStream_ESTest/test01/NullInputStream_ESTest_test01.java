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
public class NullInputStream_ESTest_test01 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that a closed NullInputStream reports zero available bytes.
     * The shared INSTANCE emulates an empty (size 0) stream, so after closing it
     * the available() method should return 0.
     */
    @Test(timeout = 4000)
    public void availableReturnsZeroAfterClose() throws Throwable {
        NullInputStream emptyStream = NullInputStream.INSTANCE;

        emptyStream.close();

        assertEquals(0, emptyStream.available());
    }
}
