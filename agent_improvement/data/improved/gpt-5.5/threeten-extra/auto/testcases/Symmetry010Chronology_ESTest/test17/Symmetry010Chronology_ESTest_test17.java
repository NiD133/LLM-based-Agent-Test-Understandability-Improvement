package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test17 extends Symmetry010Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        Symmetry010Date createdDate = chronology.INSTANCE.date(9, 10, 10);

        assertEquals(IsoEra.CE, createdDate.getEra());
    }
}
