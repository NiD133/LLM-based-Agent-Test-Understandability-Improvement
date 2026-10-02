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
public class NullInputStream_ESTest_test17 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that a default NullInputStream starts at position 0 and supports mark/reset.
     *
     * The no-arg constructor creates a size-0 stream with markSupported=true and
     * throwEofException=false. This test confirms both properties hold immediately
     * after construction.
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream();

        long initialPosition = nullInputStream0.getPosition();
        assertEquals("Stream position should be 0 at construction", 0L, initialPosition);

        assertTrue("Default NullInputStream should support mark/reset", nullInputStream0.markSupported());
    }
}
