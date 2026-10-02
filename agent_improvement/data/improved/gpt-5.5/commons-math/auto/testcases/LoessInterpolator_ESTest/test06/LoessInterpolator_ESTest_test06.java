package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test06 extends LoessInterpolator_ESTest_scaffolding {

    private static final int SAMPLE_COUNT = 4;
    private static final double SECOND_SAMPLE = 0.3;
    private static final double THIRD_SAMPLE = (double) 2;
    private static final double FOURTH_SAMPLE = 3745.0491385;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        LoessInterpolator interpolator = new LoessInterpolator();
        double[] samples = new double[SAMPLE_COUNT];
        samples[1] = SECOND_SAMPLE;
        samples[2] = THIRD_SAMPLE;
        samples[3] = FOURTH_SAMPLE;

        try {
            interpolator.smooth(samples, samples);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // bandwidth (1)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
