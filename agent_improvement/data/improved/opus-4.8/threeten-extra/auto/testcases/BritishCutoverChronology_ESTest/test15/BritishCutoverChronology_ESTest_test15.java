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
public class BritishCutoverChronology_ESTest_test15 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * The British Cutover calendar system defines exactly two eras:
     * 'Before Christ' (BC) and 'Anno Domini' (AD), mirroring the Julian eras.
     * Verify that {@link BritishCutoverChronology#eras()} reports both of them.
     */
    @Test(timeout = 4000)
    public void erasReturnsTheTwoBritishCutoverEras() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;

        List<Era> eras = chronology.eras();

        assertEquals(2, eras.size());
    }
}
