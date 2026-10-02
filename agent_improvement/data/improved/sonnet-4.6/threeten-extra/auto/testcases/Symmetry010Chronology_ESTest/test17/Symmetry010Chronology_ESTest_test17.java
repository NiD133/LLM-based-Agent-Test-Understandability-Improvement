package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test17 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that a Symmetry010Date created via the chronology singleton
     * is in the Common Era (CE), matching the IsoEra.CE era value.
     */
    @Test(timeout = 4000)
    public void dateCreatedViaChronologySingleton_hasCommonEra() throws Throwable {
        Symmetry010Date date = Symmetry010Chronology.INSTANCE.date(9, 10, 10);
        assertEquals(IsoEra.CE, date.getEra());
    }
}
