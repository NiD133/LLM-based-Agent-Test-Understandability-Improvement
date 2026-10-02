package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test05 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the valid value range of the ERA field
     * returns a non-null {@link ValueRange}.
     */
    @Test(timeout = 4000)
    public void rangeOfEraFieldIsNotNull() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        ValueRange eraRange = chronology.range(ChronoField.ERA);

        assertNotNull(eraRange);
    }
}
