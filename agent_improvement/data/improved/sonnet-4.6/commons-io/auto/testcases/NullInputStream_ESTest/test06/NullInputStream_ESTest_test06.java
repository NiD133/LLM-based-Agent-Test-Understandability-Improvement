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
public class NullInputStream_ESTest_test06 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that reset() throws IOException when the mark was set with a negative
     * read limit. A negative read limit means the marked position is immediately
     * considered expired, so any attempt to reset back to it must fail.
     */
    @Test(timeout = 4000)
    public void test06_resetThrowsWhenMarkedWithNegativeReadLimit() throws Throwable {
        // Use a large stream size so the stream itself does not interfere with the test.
        NullInputStream nullInputStream0 = new NullInputStream((long) Integer.MAX_VALUE);

        // Mark at position 0 with a negative read limit; this makes the mark
        // immediately invalid because position (0) > mark (0) + readLimit (-645).
        final int negativeReadLimit = -645;
        nullInputStream0.mark(negativeReadLimit);

        // reset() must detect that the read limit has been exceeded and throw IOException.
        try {
            nullInputStream0.reset();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Marked position [0] is no longer valid - passed the read limit [-645]
            //
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
