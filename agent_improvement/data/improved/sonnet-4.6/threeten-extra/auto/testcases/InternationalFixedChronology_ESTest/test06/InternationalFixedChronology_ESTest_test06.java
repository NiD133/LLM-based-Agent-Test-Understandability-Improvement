package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test06 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Setting the ERA field to 308 must throw DateTimeException because the
     * InternationalFixedChronology only recognises a single era (CE, value 1),
     * so the valid range is [1, 1] and 308 is out of range.
     */
    @Test(timeout = 4000)
    public void test06_withEraFieldOutOfRange_throwsDateTimeException() throws Throwable {
        InternationalFixedDate today = InternationalFixedDate.now(ZoneOffset.UTC);

        try {
            today.with(ChronoField.ERA, 308L);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // ValueRange.checkValidValue() rejects 308 because ERA is restricted to [1, 1]
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
