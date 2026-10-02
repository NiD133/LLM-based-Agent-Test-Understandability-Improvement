package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test13 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_zonedDateTime_fromTemporalAccessor_returnsNonNull() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();
        ZonedDateTime currentZonedDateTime = MockZonedDateTime.now();
        ChronoZonedDateTime<PaxDate> paxZonedDateTime = paxChronology.zonedDateTime(currentZonedDateTime);
        assertNotNull(paxZonedDateTime);
    }
}
