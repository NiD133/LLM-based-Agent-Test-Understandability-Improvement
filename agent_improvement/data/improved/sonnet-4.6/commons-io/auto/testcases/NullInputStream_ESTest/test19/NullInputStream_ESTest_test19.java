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
public class NullInputStream_ESTest_test19 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_init_resetsStateAndReturnsTheSameInstance() throws Throwable {
        // Create a NullInputStream with a negative size to exercise an unusual but valid configuration.
        // The default constructor (long size) enables mark support and disables EOF exceptions.
        NullInputStream streamWithNegativeSize = new NullInputStream((-30L));

        // init() resets internal state (position, mark, readLimit, closed flag) and returns 'this'.
        NullInputStream reinitializedStream = streamWithNegativeSize.init();

        assertFalse("Stream should not be closed after init()", reinitializedStream.isClosed());
        assertEquals("Size should remain unchanged after init()", (-30L), reinitializedStream.getSize());
        assertTrue("Mark should be supported (set by default constructor)", reinitializedStream.markSupported());
        assertEquals("Position should be reset to 0 by init()", 0L, reinitializedStream.getPosition());
    }
}
