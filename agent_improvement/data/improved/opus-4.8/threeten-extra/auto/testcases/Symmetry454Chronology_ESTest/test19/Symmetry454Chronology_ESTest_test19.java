package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.Era;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test19 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The Symmetry454 calendar system supports the same two eras as the ISO
     * calendar (BCE and CE), so {@link Symmetry454Chronology#eras()} must return
     * a list containing exactly two entries.
     */
    @Test(timeout = 4000)
    public void eras_returnsTwoEras() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        List<Era> eras = chronology.eras();

        assertEquals(2, eras.size());
    }
}
