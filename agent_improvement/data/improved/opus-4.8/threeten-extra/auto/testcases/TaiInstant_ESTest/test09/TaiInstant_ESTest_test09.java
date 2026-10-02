package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test09 extends TaiInstant_ESTest_scaffolding {

    /**
     * Subtracting a zero duration must return the very same instance unchanged.
     *
     * <p>The instant is built from a negative nano adjustment (-1660 nanos relative
     * to second -1660). The factory normalises the nanos into the range 0..999,999,999
     * by borrowing one whole second, so the instant becomes -1661 seconds plus
     * 999,998,340 nanos. Subtracting {@link Duration#ZERO} is a no-op that short-circuits
     * to {@code this}.
     */
    @Test(timeout = 4000)
    public void subtractingZeroDurationReturnsSameInstance() throws Throwable {
        TaiInstant original = TaiInstant.ofTaiSeconds(-1660L, -1660L);

        TaiInstant result = original.minus(Duration.ZERO);

        assertSame("minus(ZERO) should return the same instance", original, result);
        assertEquals("normalised seconds", -1661L, result.getTaiSeconds());
        assertEquals("normalised nano-of-second", 999998340, result.getNano());
    }
}
