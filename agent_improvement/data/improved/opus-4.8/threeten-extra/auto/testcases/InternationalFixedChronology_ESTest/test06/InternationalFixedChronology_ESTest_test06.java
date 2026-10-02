package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test06 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The International Fixed calendar only has a single era (valid era values are 1 - 1),
     * so setting the ERA field to any other value must be rejected with a DateTimeException.
     */
    @Test(timeout = 4000)
    public void settingEraToOutOfRangeValueThrowsDateTimeException() throws Throwable {
        ZoneId utcZone = ZoneOffset.UTC;
        InternationalFixedDate today = InternationalFixedDate.now(utcZone);
        TemporalField eraField = ChronoField.ERA;
        long invalidEraValue = 308L;

        try {
            today.with(eraField, invalidEraValue);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for Era (valid values 1 - 1): 308
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
