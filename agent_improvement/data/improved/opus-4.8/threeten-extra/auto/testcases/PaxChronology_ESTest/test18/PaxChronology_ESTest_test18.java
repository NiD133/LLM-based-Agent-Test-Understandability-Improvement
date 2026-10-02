package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.chrono.Era;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test18 extends PaxChronology_ESTest_scaffolding {

    /**
     * The Pax calendar system defines exactly two eras: BCE and CE.
     * Verify that eras() returns both of them.
     */
    @Test(timeout = 4000)
    public void erasReturnsTheTwoPaxEras() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();

        List<Era> eras = paxChronology.eras();

        assertEquals(2, eras.size());
    }
}
