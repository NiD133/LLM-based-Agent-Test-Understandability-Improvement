package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.Era;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test09 extends JulianChronology_ESTest_scaffolding {

    /**
     * The Julian chronology defines two eras (BC and AD), so {@link JulianChronology#eras()}
     * must return a non-empty list.
     */
    @Test(timeout = 4000)
    public void erasReturnsNonEmptyList() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;

        List<Era> eras = julianChronology.eras();

        assertFalse("Julian chronology should define at least one era", eras.isEmpty());
    }
}
