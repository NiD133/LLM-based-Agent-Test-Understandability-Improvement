package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.TemporalAccessor;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test16 extends JulianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        JulianChronology constructedChronology = new JulianChronology();
        ZonedDateTime currentZonedDateTime = MockZonedDateTime.now();

        ChronoZonedDateTime<JulianDate> julianZonedDateTime =
                constructedChronology.INSTANCE.zonedDateTime((TemporalAccessor) currentZonedDateTime);

        assertNotNull(julianZonedDateTime);
    }
}
