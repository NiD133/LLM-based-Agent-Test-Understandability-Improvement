package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test24 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that converting zero minutes into a Seconds amount yields zero seconds,
     * so {@link Seconds#isZero()} reports true.
     */
    @Test(timeout = 4000)
    public void ofZeroMinutesIsZero() throws Throwable {
        Seconds zeroSeconds = Seconds.ofMinutes(0);

        assertTrue("Zero minutes should produce a zero-length Seconds amount", zeroSeconds.isZero());
    }
}
