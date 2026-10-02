package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test1 extends BoundedReader_ESTest_scaffolding {

    private static final int MAX_CHARS = 511;

    @Test(timeout = 4000)
    public void test_readFromEmptyReaderAfterResetSkipAndMarkReturnsEOF() throws Throwable {
        // BoundedReader wrapping an empty string — underlying reader has no data
        StringReader emptySource = new StringReader("");
        BoundedReader reader = new BoundedReader(emptySource, MAX_CHARS);

        // reset() before any mark sets charsRead to INVALID (-1)
        reader.reset();
        // skip advances charsRead by 511, bringing it to 510
        reader.skip(MAX_CHARS);
        // mark with readAheadLimit=511 records current position (charsRead=510)
        reader.mark(MAX_CHARS);

        // The underlying reader is empty, so read() returns EOF (-1)
        int result = reader.read();
        assertEquals(-1, result);
    }
}
