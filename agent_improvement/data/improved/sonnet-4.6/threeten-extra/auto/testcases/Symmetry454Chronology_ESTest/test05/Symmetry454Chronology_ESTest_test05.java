package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test05 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the ERA field range on the Symmetry454 chronology
     * returns a valid, non-null ValueRange. The ERA field uses the same two eras
     * as ISO (BCE=0, CE=1), so the expected range is [0, 1].
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        ValueRange eraRange = chronology.range(ChronoField.ERA);

        assertNotNull(eraRange);
        assertEquals(0L, eraRange.getMinimum());
        assertEquals(1L, eraRange.getMaximum());
    }
}
