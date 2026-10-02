package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test20 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Resolving a date from an empty set of field values yields no date.
     * With no fields supplied there is nothing to build a DiscordianDate from,
     * so resolveDate returns null regardless of the resolver style.
     */
    @Test(timeout = 4000)
    public void resolveDate_withNoFieldValues_returnsNull() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;
        Map<TemporalField, Long> noFieldValues = new HashMap<TemporalField, Long>();

        DiscordianDate resolvedDate = chronology.resolveDate(noFieldValues, ResolverStyle.STRICT);

        assertNull(resolvedDate);
    }
}
