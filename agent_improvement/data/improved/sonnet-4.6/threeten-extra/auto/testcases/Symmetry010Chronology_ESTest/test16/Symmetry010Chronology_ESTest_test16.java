package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test16 extends Symmetry010Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        // date(Era, yearOfEra, month, dayOfMonth) requires the era to be an IsoEra instance;
        // passing null must throw ClassCastException with "Invalid era: null"
        try {
            chronology.INSTANCE.date((Era) null, (-1134), 2336, (-1134));
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.Symmetry010Chronology", e);
        }
    }
}
