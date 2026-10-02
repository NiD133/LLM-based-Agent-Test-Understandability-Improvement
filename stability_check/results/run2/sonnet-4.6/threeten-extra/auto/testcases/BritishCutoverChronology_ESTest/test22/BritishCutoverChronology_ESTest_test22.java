package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test22 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that resolveDate returns null when no temporal fields are provided,
     * since there is insufficient information to construct a date.
     */
    @Test(timeout = 4000)
    public void test_resolveDate_withEmptyFieldMap_returnsNull() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;
        HashMap<TemporalField, Long> emptyFieldValues = new HashMap<>();

        BritishCutoverDate result = chronology.resolveDate(emptyFieldValues, ResolverStyle.STRICT);

        assertNull(result);
    }
}
