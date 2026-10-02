package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test18 extends JulianChronology_ESTest_scaffolding {

    /**
     * Resolving a date from an empty set of field values yields no date,
     * so resolveDate should return null regardless of resolver style.
     */
    @Test(timeout = 4000)
    public void resolveDateWithNoFieldsReturnsNull() throws Throwable {
        JulianChronology chronology = new JulianChronology();
        Map<TemporalField, Long> emptyFieldValues = new HashMap<TemporalField, Long>();

        JulianDate resolvedDate = chronology.resolveDate(emptyFieldValues, ResolverStyle.STRICT);

        assertNull(resolvedDate);
    }
}
