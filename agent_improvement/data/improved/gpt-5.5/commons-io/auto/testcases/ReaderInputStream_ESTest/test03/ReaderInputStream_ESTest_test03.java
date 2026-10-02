package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test03 extends ReaderInputStream_ESTest_scaffolding {

    private static final String SINGLE_CHARACTER_INPUT = "t";
    private static final long REQUESTED_SKIP_COUNT = 1884L;
    private static final long EXPECTED_SKIPPED_BYTES = 1L;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        StringReader sourceReader = new StringReader(SINGLE_CHARACTER_INPUT);
        ReaderInputStream inputStream = new ReaderInputStream(sourceReader);

        long skippedBytes = inputStream.skip(REQUESTED_SKIP_COUNT);

        assertEquals(EXPECTED_SKIPPED_BYTES, skippedBytes);
    }
}
