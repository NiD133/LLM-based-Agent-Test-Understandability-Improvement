package org.threeten.extra.chrono;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test14 extends BritishCutoverChronology_ESTest_scaffolding {

    private static final String BRITISH_CUTOVER_CHRONOLOGY_ID = "BritishCutover";

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        String chronologyId = chronology.getId();

        assertEquals(BRITISH_CUTOVER_CHRONOLOGY_ID, chronologyId);
    }
}
