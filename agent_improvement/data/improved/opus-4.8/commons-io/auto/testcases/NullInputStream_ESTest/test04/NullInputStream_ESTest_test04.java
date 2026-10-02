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
public class NullInputStream_ESTest_test04 extends NullInputStream_ESTest_scaffolding {

    /**
     * Skipping on the shared empty {@link NullInputStream#INSTANCE} (size 0) is
     * already at end-of-file, so it returns -1 because the singleton does not
     * throw an EOFException. A separate size-1 stream that is left untouched
     * still reports 1 available byte and no mark support.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        NullInputStream sizeOneStream = new NullInputStream(1L, false, false);

        // INSTANCE is the size-0 singleton; skipping at EOF returns -1.
        long skipped = NullInputStream.INSTANCE.skip(0L);
        assertEquals((-1L), skipped);

        // The untouched size-1 stream is unaffected by the skip above.
        assertEquals(1, sizeOneStream.available());
        assertFalse(sizeOneStream.markSupported());
    }
}
