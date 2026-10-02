package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test14 extends Symmetry010Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14_rangeForAlignedDayOfWeekInYear_returnsNonNull() throws Throwable {
        // Verify that querying the value range for ALIGNED_DAY_OF_WEEK_IN_YEAR returns a valid (non-null) range
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        ValueRange alignedDayOfWeekInYearRange = chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
        assertNotNull(alignedDayOfWeekInYearRange);
    }
}
