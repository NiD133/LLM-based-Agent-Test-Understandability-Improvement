package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test02 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_range_returnsValueRange_forYearOfEraField() throws Throwable {
        DiscordianChronology discordianChronology0 = new DiscordianChronology();
        ChronoField chronoField0 = ChronoField.YEAR_OF_ERA;
        ValueRange valueRange0 = discordianChronology0.range(chronoField0);
        assertNotNull(valueRange0);
    }
}
