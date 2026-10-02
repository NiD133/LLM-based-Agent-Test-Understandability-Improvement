package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test21 extends Symmetry010Chronology_ESTest_scaffolding {

    private static final long EPOCH_DAY_IN_COMMON_ERA = 702L;

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        Symmetry010Date dateFromEpochDay = chronology.INSTANCE.dateEpochDay(EPOCH_DAY_IN_COMMON_ERA);

        assertEquals(IsoEra.CE, dateFromEpochDay.getEra());
    }
}
