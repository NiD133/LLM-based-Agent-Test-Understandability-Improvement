package org.apache.commons.math4.legacy.ml.clustering.evaluation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import org.apache.commons.math4.legacy.ml.clustering.Cluster;
import org.apache.commons.math4.legacy.ml.clustering.DoublePoint;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CalinskiHarabasz_ESTest_test2 extends CalinskiHarabasz_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        CalinskiHarabasz calinskiHarabasz0 = new CalinskiHarabasz();
        boolean boolean0 = calinskiHarabasz0.isBetterScore(1023.5644983, 2869.5372);
        assertFalse(boolean0);
    }
}
