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
public class NullReader_ESTest_test01 extends NullReader_ESTest_scaffolding {

    private static final long NEGATIVE_READER_SIZE = -827L;
    private static final long CHARS_TO_SKIP = 382L;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        NullReader readerWithNegativeSize = new NullReader(NEGATIVE_READER_SIZE);

        long skippedChars = readerWithNegativeSize.skip(CHARS_TO_SKIP);

        assertEquals(NEGATIVE_READER_SIZE, readerWithNegativeSize.getPosition());
        assertEquals(NEGATIVE_READER_SIZE, skippedChars);
    }
}
