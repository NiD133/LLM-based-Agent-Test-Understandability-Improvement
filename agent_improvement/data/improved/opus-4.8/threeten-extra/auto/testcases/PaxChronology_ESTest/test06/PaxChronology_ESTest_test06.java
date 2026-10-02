package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test06 extends PaxChronology_ESTest_scaffolding {

    /**
     * For the Common Era (CE), the proleptic year equals the year-of-era,
     * so prolepticYear(CE, 99) should return 99 unchanged.
     */
    @Test(timeout = 4000)
    public void prolepticYearForCommonEraEqualsYearOfEra() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();

        int prolepticYear = paxChronology.INSTANCE.prolepticYear(PaxEra.CE, 99);

        assertEquals(99, prolepticYear);
    }
}
