package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.JapaneseEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test02 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the Discordian chronology for the value range of the
     * YEAR_OF_ERA field returns a (non-null) ValueRange describing the supported years.
     */
    @Test(timeout = 4000)
    public void rangeForYearOfEraFieldReturnsValueRange() throws Throwable {
        DiscordianChronology discordianChronology = new DiscordianChronology();

        ValueRange yearOfEraRange = discordianChronology.range(ChronoField.YEAR_OF_ERA);

        assertNotNull(yearOfEraRange);
    }
}
