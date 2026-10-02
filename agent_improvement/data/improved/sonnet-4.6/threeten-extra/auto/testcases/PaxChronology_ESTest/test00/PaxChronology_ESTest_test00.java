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
     * Verifies that applying a ZoneOffset as a TemporalAdjuster to a PaxDate throws
     * UnsupportedTemporalTypeException, because PaxDate does not support the OffsetSeconds field
     * that ZoneOffset.MIN tries to adjust.
     */
    @Test(timeout = 4000)
    public void test00_applyingZoneOffsetAdjusterToPaxDateThrowsUnsupportedTemporalTypeException() throws Throwable {
        PaxDate today = PaxChronology.INSTANCE.dateNow();
        ZoneOffset minOffset = ZoneOffset.MIN;

        try {
            today.with((TemporalAdjuster) minOffset);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // ZoneOffset.MIN adjusts by writing OffsetSeconds, which PaxDate does not support
            verifyException("org.threeten.extra.chrono.AbstractDate", e);
        }
    }
}
