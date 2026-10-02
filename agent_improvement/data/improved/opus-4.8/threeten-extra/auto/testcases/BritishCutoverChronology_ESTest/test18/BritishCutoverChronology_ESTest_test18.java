package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test18 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link BritishCutoverChronology#eraOf(int)} maps the
     * era value 0 to the "Before Christ" era ({@link JulianEra#BC}).
     */
    @Test(timeout = 4000)
    public void eraOfZeroReturnsBeforeChristEra() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        JulianEra era = chronology.eraOf(0);

        assertEquals(JulianEra.BC, era);
    }
}
