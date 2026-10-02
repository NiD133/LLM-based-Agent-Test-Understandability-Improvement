package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test00 extends BritishCutoverChronology_ESTest_scaffolding {

    // SECOND_OF_DAY is not a calendar-specific field, so range() delegates to the field's
    // own range() rather than returning a British-Cutover-specific override.
    @Test(timeout = 4000)
    public void range_withSecondOfDayField_returnsNonNullValueRange() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        ValueRange secondOfDayRange = chronology.range(ChronoField.SECOND_OF_DAY);
        assertNotNull(secondOfDayRange);
    }
}
