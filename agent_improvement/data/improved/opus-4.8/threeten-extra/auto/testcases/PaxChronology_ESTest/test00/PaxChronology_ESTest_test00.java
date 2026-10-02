package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test00 extends PaxChronology_ESTest_scaffolding {

    /**
     * Adjusting a PaxDate with a ZoneOffset must fail.
     * <p>
     * A {@link ZoneOffset} is a {@link TemporalAdjuster} that tries to set the
     * OFFSET_SECONDS field, but PaxDate (via AbstractDate) does not support that
     * field, so the adjustment is expected to raise
     * {@link UnsupportedTemporalTypeException}.
     */
    @Test(timeout = 4000)
    public void adjustingPaxDateWithZoneOffsetThrowsUnsupportedField() throws Throwable {
        PaxDate today = PaxChronology.INSTANCE.dateNow();
        TemporalAdjuster offsetAdjuster = ZoneOffset.MIN;

        try {
            today.with(offsetAdjuster);
            fail("Expected UnsupportedTemporalTypeException for unsupported field OffsetSeconds");
        } catch (UnsupportedTemporalTypeException expected) {
            // PaxDate inherits its field handling from AbstractDate, which rejects OffsetSeconds.
            verifyException("org.threeten.extra.chrono.AbstractDate", expected);
        }
    }
}
