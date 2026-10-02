package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalUnit;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test25 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that the units supported by an Hours amount are reported.
     * Hours.ZERO supports exactly the HOURS unit, so getUnits() must return
     * a non-empty list.
     */
    @Test(timeout = 4000)
    public void getUnits_returnsNonEmptyList() throws Throwable {
        Hours zeroHours = Hours.ZERO;

        List<TemporalUnit> supportedUnits = zeroHours.getUnits();

        assertFalse("Hours should support at least one temporal unit", supportedUnits.isEmpty());
    }
}
