package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test04 extends JulianChronology_ESTest_scaffolding {

    /**
     * For AD era, prolepticYear(era, yearOfEra) returns yearOfEra unchanged,
     * because AD years map directly to positive proleptic years.
     */
    @Test(timeout = 4000)
    public void test_prolepticYear_adEra_returnsYearOfEraUnchanged() throws Throwable {
        JulianChronology chronology = new JulianChronology();
        int prolepticYear = chronology.prolepticYear(JulianEra.AD, 652);
        assertEquals(652, prolepticYear);
    }
}
