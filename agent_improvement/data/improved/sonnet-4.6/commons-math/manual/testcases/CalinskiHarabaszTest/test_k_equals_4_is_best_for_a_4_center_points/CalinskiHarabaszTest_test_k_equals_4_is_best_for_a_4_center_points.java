package org.apache.commons.math4.legacy.ml.clustering.evaluation;

import org.apache.commons.math4.legacy.ml.clustering.CentroidCluster;
import org.apache.commons.math4.legacy.ml.clustering.ClusterEvaluator;
import org.apache.commons.math4.legacy.ml.clustering.DoublePoint;
import org.apache.commons.math4.legacy.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math4.legacy.ml.distance.DistanceMeasure;
import org.apache.commons.math4.legacy.ml.distance.EuclideanDistance;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class CalinskiHarabaszTest_test_k_equals_4_is_best_for_a_4_center_points {

    /** The four true cluster centers around which synthetic data is generated. */
    private static final double[][] CLUSTER_CENTERS = { { -1, -1 }, { 0, 0 }, { 1, 1 }, { 2, 2 } };

    /** Total number of synthetic data points — 1000 spread evenly across the 4 centers. */
    private static final int NUM_POINTS = 1000;

    /** Maximum absolute offset added to each coordinate when placing a point near its center. */
    private static final double MAX_OFFSET = 0.25;

    private ClusterEvaluator evaluator;
    private DistanceMeasure distanceMeasure;

    @Before
    public void setUp() {
        evaluator = new CalinskiHarabasz();
        distanceMeasure = new EuclideanDistance();
    }

    /**
     * Verifies that the Calinski-Harabasz index correctly identifies k=4 as the best
     * number of clusters when the data was generated from exactly 4 cluster centers.
     *
     * The test sweeps k from 2 to 6 and asserts that the score is highest at k=4.
     */
    @Test
    public void test_k_equals_4_is_best_for_a_4_center_points() {
        final int dimension = 2;
        final UniformRandomProvider rnd = RandomSource.MT_64.create();

        // Build a synthetic dataset: 1000 points placed near the 4 true cluster centers,
        // cycling evenly so each center receives 250 points.
        final List<DoublePoint> points = new ArrayList<>();
        for (int i = 0; i < NUM_POINTS; i++) {
            double[] center = CLUSTER_CENTERS[i % CLUSTER_CENTERS.length];
            double[] point = new double[dimension];
            for (int j = 0; j < dimension; j++) {
                double offset = (rnd.nextDouble() - 0.5) / 2;
                Assert.assertTrue(offset < MAX_OFFSET && offset > -MAX_OFFSET);
                point[j] = offset + center[j];
            }
            points.add(new DoublePoint(point));
        }

        // Sweep k from 2 to (trueCenters + 2), i.e. 2..6, and record the best score
        // and the score specifically at the true number of centers.
        final int trueK = CLUSTER_CENTERS.length; // k=4 matches the data generation
        double highestScore = 0.0;
        double scoreAtTrueK = 0.0;

        for (int k = 2; k <= trueK + 2; k++) {
            KMeansPlusPlusClusterer<DoublePoint> kMeans =
                    new KMeansPlusPlusClusterer<>(k, Integer.MAX_VALUE, distanceMeasure, rnd);
            List<CentroidCluster<DoublePoint>> clusters = kMeans.cluster(points);
            double score = evaluator.score(clusters);
            if (score > highestScore) {
                highestScore = score;
            }
            if (k == trueK) {
                scoreAtTrueK = score;
            }
        }

        // k=4 must produce the highest Calinski-Harabasz score across all evaluated k values.
        Assert.assertEquals(highestScore, scoreAtTrueK, 0.0);
    }
}
