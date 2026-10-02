package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test14 extends BritishCutoverChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void getIdReturnsBritishCutover() throws Throwable {
        BritishCutoverChronology britishCutoverChronology0 = new BritishCutoverChronology();
        String string0 = britishCutoverChronology0.getId();
        assertEquals("BritishCutover", string0);
    }
}
