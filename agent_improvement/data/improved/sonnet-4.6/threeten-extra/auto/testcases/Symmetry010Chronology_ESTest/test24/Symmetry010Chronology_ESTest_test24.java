package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.TemporalAccessor;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test24 extends Symmetry010Chronology_ESTest_scaffolding {

    // Verifies that an OffsetDateTime can be converted to a Symmetry010 zoned date-time
    @Test(timeout = 4000)
    public void test_zonedDateTime_fromOffsetDateTime_returnsNonNull() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        OffsetDateTime offsetDateTime = MockOffsetDateTime.now();
        ChronoZonedDateTime<Symmetry010Date> zonedDateTime = chronology.zonedDateTime((TemporalAccessor) offsetDateTime);
        assertNotNull(zonedDateTime);
    }
}
