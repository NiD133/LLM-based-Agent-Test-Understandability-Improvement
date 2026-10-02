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
public class CalinskiHarabasz_ESTest_test3 extends CalinskiHarabasz_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        CalinskiHarabasz calinskiHarabasz0 = new CalinskiHarabasz();
        LinkedList<Cluster<DoublePoint>> linkedList0 = new LinkedList<Cluster<DoublePoint>>();
        Cluster<DoublePoint> cluster0 = new Cluster<DoublePoint>();
        int[] intArray0 = new int[5];
        DoublePoint doublePoint0 = new DoublePoint(intArray0);
        cluster0.addPoint(doublePoint0);
        intArray0[4] = 71;
        DoublePoint doublePoint1 = new DoublePoint(intArray0);
        cluster0.addPoint(doublePoint1);
        linkedList0.add(cluster0);
        double double0 = calinskiHarabasz0.score(linkedList0);
        assertEquals(Double.NaN, double0, 0.01);
    }
}
