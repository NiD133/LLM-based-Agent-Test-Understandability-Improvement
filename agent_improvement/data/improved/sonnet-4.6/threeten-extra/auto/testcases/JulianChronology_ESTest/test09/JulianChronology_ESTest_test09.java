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

    // JulianChronology defines two eras: BC (Before Christ) and AD (Anno Domini).
    // This test verifies that eras() returns a non-empty list, confirming that
    // the Julian calendar system exposes at least one era.
    @Test(timeout = 4000)
    public void test_eras_returnsNonEmptyList() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;
        List<Era> eras = julianChronology.eras();
        assertFalse(eras.isEmpty());
    }
}
