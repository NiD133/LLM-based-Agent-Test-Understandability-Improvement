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
public class BritishCutoverChronology_ESTest_test22 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Resolving a date from an empty set of field values yields no date.
     * With nothing to resolve, resolveDate should return null regardless of the resolver style.
     */
    @Test(timeout = 4000)
    public void resolveDate_withNoFieldValues_returnsNull() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        Map<TemporalField, Long> emptyFieldValues = new HashMap<TemporalField, Long>();

        BritishCutoverDate resolvedDate = chronology.resolveDate(emptyFieldValues, ResolverStyle.STRICT);

        assertNull(resolvedDate);
    }
}
