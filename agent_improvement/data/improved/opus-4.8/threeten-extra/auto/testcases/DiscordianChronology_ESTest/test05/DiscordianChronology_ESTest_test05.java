package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test05 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the valid value range for the ERA field
     * returns a defined (non-null) range.
     */
    @Test(timeout = 4000)
    public void rangeForEraFieldIsDefined() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        ValueRange eraRange = chronology.range(ChronoField.ERA);

        assertNotNull(eraRange);
    }
}
