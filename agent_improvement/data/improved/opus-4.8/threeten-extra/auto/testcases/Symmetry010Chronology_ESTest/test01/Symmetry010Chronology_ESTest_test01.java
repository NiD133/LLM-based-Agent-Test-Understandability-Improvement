package org.threeten.extra.chrono;

import static org.junit.Assert.assertNotNull;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test01 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * The Symmetry010 chronology should expose a valid range for the YEAR field.
     */
    @Test(timeout = 4000)
    public void rangeOfYearFieldIsDefined() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        ValueRange yearRange = chronology.range(ChronoField.YEAR);

        assertNotNull(yearRange);
    }
}
