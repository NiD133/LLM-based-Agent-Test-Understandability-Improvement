package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test18 extends Symmetry454Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18_dateYearDay_withOutOfRangeDayOfYear_throwsDateTimeException() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        IsoEra bceEra = IsoEra.BCE;
        // Day-of-year 4960 exceeds the valid range (1-364/371) and must be rejected
        try {
            chronology.dateYearDay((Era) bceEra, 4960, 4960);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
