package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.chrono.Era;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test22 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * The Symmetry010 calendar shares its eras with the Gregorian calendar,
     * so {@link Symmetry010Chronology#eras()} should return exactly the two
     * ISO eras: BCE and CE.
     */
    @Test(timeout = 4000)
    public void erasReturnsTheTwoIsoEras() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        List<Era> eras = chronology.eras();

        assertEquals(2, eras.size());
    }
}
