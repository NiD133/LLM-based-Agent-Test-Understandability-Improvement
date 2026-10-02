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
public class InternationalFixedChronology_ESTest_test25 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The International Fixed chronology supports exactly one era (the current
     * 'Common Era'), so {@link InternationalFixedChronology#eras()} must return
     * a non-empty list.
     */
    @Test(timeout = 4000)
    public void erasListIsNotEmpty() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;

        List<Era> eras = chronology.eras();

        assertFalse(eras.isEmpty());
    }
}
