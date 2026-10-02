package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test05 extends Symmetry010Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void rangeOfEraField_returnsNonNullValueRange() throws Throwable {
        // The Symmetry010 chronology shares BCE/CE eras with ISO, so ERA range should be [0, 1]
        ValueRange eraRange = Symmetry010Chronology.INSTANCE.range(ChronoField.ERA);
        assertNotNull(eraRange);
    }
}
