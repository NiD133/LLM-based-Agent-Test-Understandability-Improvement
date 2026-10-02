package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test18 extends JulianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_resolveDate_withEmptyFieldMap_returnsNull() throws Throwable {
        JulianChronology chronology = new JulianChronology();
        HashMap<TemporalField, Long> emptyFieldValues = new HashMap<TemporalField, Long>();
        JulianDate result = chronology.resolveDate(emptyFieldValues, ResolverStyle.STRICT);
        assertNull(result);
    }
}
