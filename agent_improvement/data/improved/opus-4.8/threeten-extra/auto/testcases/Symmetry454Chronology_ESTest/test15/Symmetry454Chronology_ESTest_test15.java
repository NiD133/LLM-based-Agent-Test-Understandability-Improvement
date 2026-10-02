package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test15 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The chronology's identifier should be the fixed string "Sym454".
     */
    @Test(timeout = 4000)
    public void getId_returnsSym454() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        String id = chronology.getId();

        assertEquals("Sym454", id);
    }
}
