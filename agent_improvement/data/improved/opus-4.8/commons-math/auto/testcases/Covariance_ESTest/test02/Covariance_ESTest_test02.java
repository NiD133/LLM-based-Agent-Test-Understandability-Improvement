package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test02 extends Covariance_ESTest_scaffolding {

    /**
     * Covariance requires at least two observed points per sample. Passing
     * single-element arrays must therefore be rejected with a RuntimeException
     * thrown from the Covariance class itself.
     */
    @Test(timeout = 4000)
    public void covarianceWithSingleObservationThrowsException() throws Throwable {
        Covariance covariance = new Covariance();
        double[] sampleWithOneObservation = new double[1];

        try {
            covariance.covariance(sampleWithOneObservation, sampleWithOneObservation);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // sample contains 1 observed points, at least 2 are required
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }
}
