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

    /**
     * Once the number of characters consumed reaches the configured limit,
     * {@link BoundedReader#read()} must return EOF (-1) regardless of the
     * underlying reader's state.
     *
     * <p>Here the limit is 511 characters. Skipping 511 characters advances the
     * internal counter up to that limit, so the subsequent {@code read()}
     * reports EOF.</p>
     */
    @Test(timeout = 4000)
    public void readReturnsEofOnceCharacterLimitIsReached() throws Throwable {
        final int maxCharsFromTarget = 511;
        StringReader emptyTarget = new StringReader("");
        BoundedReader boundedReader = new BoundedReader(emptyTarget, maxCharsFromTarget);

        boundedReader.reset();
        boundedReader.skip(maxCharsFromTarget);
        boundedReader.mark(maxCharsFromTarget);

        int charRead = boundedReader.read();

        assertEquals("read() should report EOF once the character limit is reached", -1, charRead);
    }
}
