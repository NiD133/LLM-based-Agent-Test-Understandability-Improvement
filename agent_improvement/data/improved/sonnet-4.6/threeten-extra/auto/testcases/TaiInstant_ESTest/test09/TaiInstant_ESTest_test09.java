package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test09 extends TaiInstant_ESTest_scaffolding {

    // ofTaiSeconds normalizes a negative nanoAdjustment by borrowing from the seconds field.
    // -1660 nanos borrows 1 second, yielding seconds=-1661 and nanos=999_998_340.
    // minus(Duration.ZERO) detects the zero duration and returns "this" unchanged (identity optimization).
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // -1660 nanos is normalized: borrow 1 second → stored as (-1661s, 999_998_340ns)
        TaiInstant instantWithNegativeNanos = TaiInstant.ofTaiSeconds(-1660L, -1660L);

        // Subtracting zero duration must return the exact same instance
        TaiInstant resultAfterSubtractingZero = instantWithNegativeNanos.minus(Duration.ZERO);

        assertEquals(999998340, resultAfterSubtractingZero.getNano());
        assertEquals(-1661L, resultAfterSubtractingZero.getTaiSeconds());
        // minus(ZERO) returns "this" — verify the identity optimization
        assertSame(resultAfterSubtractingZero, instantWithNegativeNanos);
    }
}
