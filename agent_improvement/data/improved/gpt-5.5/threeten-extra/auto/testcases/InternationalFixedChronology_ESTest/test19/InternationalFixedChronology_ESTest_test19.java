package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test19 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        InternationalFixedDate currentFixedDate = chronology.dateNow();

        assertEquals(365, currentFixedDate.lengthOfYear());
    }
}
