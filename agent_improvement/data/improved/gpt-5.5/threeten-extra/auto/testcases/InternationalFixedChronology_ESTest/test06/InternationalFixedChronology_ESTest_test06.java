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

    private static final long INVALID_ERA_VALUE = 308L;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        ZoneOffset utcZone = ZoneOffset.UTC;
        InternationalFixedDate currentUtcDate = InternationalFixedDate.now((ZoneId) utcZone);
        ChronoField eraField = ChronoField.ERA;

        try {
            currentUtcDate.with((TemporalField) eraField, INVALID_ERA_VALUE);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
