package org.threeten.extra;

import static org.junit.Assert.assertNull;

import java.time.temporal.Temporal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test07 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that adding a zero-second amount to a temporal returns the
     * temporal unchanged. Because {@link Seconds#addTo} short-circuits when the
     * amount is zero, the supplied {@code null} temporal is returned as-is
     * rather than triggering a NullPointerException.
     */
    @Test(timeout = 4000)
    public void addTo_zeroSeconds_returnsTemporalUnchanged() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        Temporal result = zeroSeconds.addTo((Temporal) null);

        assertNull("Adding zero seconds should return the input temporal unchanged", result);
    }
}
