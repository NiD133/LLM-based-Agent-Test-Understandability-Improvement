package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.temporal.TemporalAdjuster;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test13 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Adjusting an International Fixed date with an equivalent Ethiopic date (used as a
     * TemporalAdjuster) should yield the International Fixed date that represents the same
     * point on the timeline as that Ethiopic date.
     */
    @Test(timeout = 4000)
    public void adjustingWithEquivalentEthiopicDateProducesSameInstantDate() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        // Start from year 7, day-of-year 7 in the International Fixed calendar.
        InternationalFixedDate startDate = chronology.dateYearDay(7, 7);

        // Convert that date to the Ethiopic calendar; it denotes the same instant.
        EthiopicDate equivalentEthiopicDate = EthiopicDate.from(startDate);

        // Adjusting the original date with the Ethiopic date snaps it to the Ethiopic date's instant.
        InternationalFixedDate adjustedDate = startDate.with((TemporalAdjuster) equivalentEthiopicDate);

        assertEquals(-716965L, adjustedDate.toEpochDay());
        assertEquals(365, adjustedDate.lengthOfYear());
    }
}
