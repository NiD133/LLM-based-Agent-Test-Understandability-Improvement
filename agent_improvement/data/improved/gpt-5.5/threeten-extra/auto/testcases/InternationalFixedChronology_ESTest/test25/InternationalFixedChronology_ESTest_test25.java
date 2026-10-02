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
public class InternationalFixedChronology_ESTest_test25 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        List<Era> eras = chronology.INSTANCE.eras();

        assertFalse(eras.isEmpty());
    }
}
