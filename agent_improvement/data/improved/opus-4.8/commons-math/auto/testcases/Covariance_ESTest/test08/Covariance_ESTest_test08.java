package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test08 extends Covariance_ESTest_scaffolding {

    /**
     * A Covariance created with the no-argument constructor starts out empty:
     * its covariance matrix is available and it reports a sample count (N) of zero.
     */
    @Test(timeout = 4000)
    public void emptyCovarianceReportsZeroObservations() throws Throwable {
        Covariance emptyCovariance = new Covariance();

        emptyCovariance.getCovarianceMatrix();

        assertEquals(0, emptyCovariance.getN());
    }
}
