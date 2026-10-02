package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test06 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that reset() fails when the configured read limit is negative.
     *
     * <p>The stream is marked at position 0 with a negative read limit (-645).
     * Since reset() checks {@code position > mark + readLimit} (i.e. {@code 0 > 0 + (-645)}),
     * the marked position is immediately considered invalid and an IOException is thrown.</p>
     */
    @Test(timeout = 4000)
    public void resetWithNegativeReadLimitThrowsIOException() throws Throwable {
        NullInputStream stream = new NullInputStream(2147483647L);
        int negativeReadLimit = -645;
        stream.mark(negativeReadLimit);

        try {
            stream.reset();
            fail("Expected IOException because the marked position is past the negative read limit");
        } catch (IOException e) {
            // Message: "Marked position [0] is no longer valid - passed the read limit [-645]"
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
