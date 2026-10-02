package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test05 extends Covariance_ESTest_scaffolding {

    /**
     * A Covariance created with the no-argument constructor holds no data,
     * so the number of observations reported by getN() should be zero.
     */
    @Test(timeout = 4000)
    public void getN_onEmptyCovariance_returnsZero() throws Throwable {
        Covariance emptyCovariance = new Covariance();

        int numberOfObservations = emptyCovariance.getN();

        assertEquals(0, numberOfObservations);
    }
}
