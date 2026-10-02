package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test06 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that prolepticYear returns the year-of-era unchanged when the era is CE,
     * since CE years map directly to positive proleptic years.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        int prolepticYear = PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 99);
        assertEquals(99, prolepticYear);
    }
}
