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
public class NullInputStream_ESTest_test09 extends NullInputStream_ESTest_scaffolding {

    /**
     * The shared {@link NullInputStream#INSTANCE} emulates an empty (size 0) stream.
     * Reading into a byte array therefore immediately hits end-of-file and, because the
     * instance is configured not to throw {@link EOFException}, returns -1.
     */
    @Test(timeout = 4000)
    public void readIntoArrayOnEmptyStreamReturnsEof() throws Throwable {
        NullInputStream emptyStream = NullInputStream.INSTANCE;
        byte[] buffer = new byte[1];

        int bytesRead = emptyStream.read(buffer);

        assertEquals("Reading an empty stream should report end-of-file", -1, bytesRead);
    }
}
