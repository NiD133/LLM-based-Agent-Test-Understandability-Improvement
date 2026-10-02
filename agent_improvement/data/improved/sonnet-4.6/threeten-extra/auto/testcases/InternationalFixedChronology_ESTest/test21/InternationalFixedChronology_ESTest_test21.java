package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test21 extends InternationalFixedChronology_ESTest_scaffolding {

    // The International Fixed calendar uses year 12, month 12, day 12.
    // Year 12 is in the first century CE, well before the Unix epoch (1970),
    // so the epoch day is a large negative number.
    @Test(timeout = 4000)
    public void test_date_inEarlyCentury_returnsNegativeEpochDay() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedDate date = chronology.date(12, 12, 12);
        assertEquals(-714825L, date.toEpochDay());
    }
}
