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

/**
 * Verifies that the Calinski-Harabasz cluster evaluator awards its highest score
 * to the clustering whose number of clusters matches the number of clusters that
 * actually exist in the data.
 *
 * <p>The data is built as 1000 points scattered tightly around 4 well-separated
 * centers, so the "true" number of clusters is 4. We cluster the same points for
 * k = 2, 3, 4, 5 and 6, score each result, and confirm that k = 4 produces the
 * best (highest) score.
 */
public class CalinskiHarabaszTest_test_k_equals_4_is_best_for_a_4_center_points {

    /** The four centers the generated points are scattered around. */
    private static final double[][] CENTERS = { { -1, -1 }, { 0, 0 }, { 1, 1 }, { 2, 2 } };

    /** Each generated point has this many coordinates. */
    private static final int DIMENSION = 2;

    /** Number of points to generate for the test. */
    private static final int POINT_COUNT = 1000;

    /** Maximum absolute distance a point may be offset from its center on any axis. */
    private static final double MAX_OFFSET = 0.25;

    private ClusterEvaluator evaluator;
    private DistanceMeasure distanceMeasure;

    @Before
    public void setUp() {
        evaluator = new CalinskiHarabasz();
        distanceMeasure = new EuclideanDistance();
    }

    @Test
    public void test_k_equals_4_is_best_for_a_4_center_points() {
        final UniformRandomProvider rng = RandomSource.MT_64.create();
        final List<DoublePoint> points = generatePointsAroundCenters(rng);

        // The number of clusters that actually exists in the data.
        final int trueClusterCount = CENTERS.length;

        double highestScore = 0.0;
        double scoreAtTrueClusterCount = 0.0;

        // Cluster the points for k = 2, 3, 4, 5, 6 and score each clustering.
        for (int k = 2; k <= 6; k++) {
            final double score = clusterAndScore(points, k, rng);

            if (score > highestScore) {
                highestScore = score;
            }
            if (k == trueClusterCount) {
                scoreAtTrueClusterCount = score;
            }
        }

        // The best score must be the one obtained at the true number of clusters (k = 4).
        Assert.assertEquals(highestScore, scoreAtTrueClusterCount, 0.0);
    }

    /**
     * Generates {@link #POINT_COUNT} points, distributing them evenly across the
     * four {@link #CENTERS} and offsetting each coordinate by a small random amount.
     */
    private List<DoublePoint> generatePointsAroundCenters(final UniformRandomProvider rng) {
        final List<DoublePoint> points = new ArrayList<>();
        for (int i = 0; i < POINT_COUNT; i++) {
            final double[] center = CENTERS[i % CENTERS.length];
            final double[] coordinates = new double[DIMENSION];
            for (int axis = 0; axis < DIMENSION; axis++) {
                final double offset = (rng.nextDouble() - 0.5) / 2;
                // Keep clusters well separated: the offset stays within (-0.25, 0.25).
                Assert.assertTrue(offset < MAX_OFFSET && offset > -MAX_OFFSET);
                coordinates[axis] = center[axis] + offset;
            }
            points.add(new DoublePoint(coordinates));
        }
        return points;
    }

    /**
     * Clusters the given points into {@code k} clusters with k-means++ and returns
     * the Calinski-Harabasz score of the resulting clustering.
     */
    private double clusterAndScore(final List<DoublePoint> points, final int k,
                                   final UniformRandomProvider rng) {
        final KMeansPlusPlusClusterer<DoublePoint> kMeans =
                new KMeansPlusPlusClusterer<>(k, Integer.MAX_VALUE, distanceMeasure, rng);
        final List<CentroidCluster<DoublePoint>> clusters = kMeans.cluster(points);
        return evaluator.score(clusters);
    }
}
