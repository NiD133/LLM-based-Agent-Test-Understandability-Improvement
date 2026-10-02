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

    /**
     * When the emulated size is negative, skipping pushes the position past
     * that size, so {@link NullReader#skip(long)} clamps the position back to
     * the size and reports the (negative) number of characters actually skipped.
     */
    @Test(timeout = 4000)
    public void skipBeyondNegativeSizeClampsPositionToSize() throws Throwable {
        final long negativeSize = -827L;
        NullReader reader = new NullReader(negativeSize);

        long charactersSkipped = reader.skip(382L);

        assertEquals("position is clamped to the emulated size", negativeSize, reader.getPosition());
        assertEquals("skip reports characters skipped up to the size", negativeSize, charactersSkipped);
    }
}
