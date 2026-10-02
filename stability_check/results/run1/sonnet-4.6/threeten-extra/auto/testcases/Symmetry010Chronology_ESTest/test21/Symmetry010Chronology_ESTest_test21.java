package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test21 extends Symmetry010Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        // A positive epoch day maps to a date in the Common Era (CE)
        Symmetry010Date dateAtEpochDay702 = Symmetry010Chronology.INSTANCE.dateEpochDay(702L);
        assertEquals(IsoEra.CE, dateAtEpochDay702.getEra());
    }
}
