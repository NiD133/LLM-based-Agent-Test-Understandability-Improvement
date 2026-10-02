package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test18 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        PaxChronology chronology = new PaxChronology();

        List<Era> eras = chronology.eras();

        assertEquals(2, eras.size());
    }
}
