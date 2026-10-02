package org.apache.commons.math4.legacy.ml.clustering.evaluation;

import java.util.ArrayList;
import java.util.List;

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

public class CalinskiHarabaszTest_test_k_equals_4_is_best_for_a_4_center_points {

    private static final int POINT_DIMENSION = 2;
    private static final int POINT_COUNT = 1000;
    private static final int MIN_CLUSTER_COUNT = 2;
    private static final int TESTED_CLUSTER_COUNTS = 5;
    private static final double MAX_OFFSET_FROM_CENTER = 0.25;

    private static final double[][] FOUR_CENTER_POINTS = {
        { -1, -1 },
        { 0, 0 },
        { 1, 1 },
        { 2, 2 }
    };

    private ClusterEvaluator evaluator;
    private DistanceMeasure distanceMeasure;

    @Before
    public void setUp() {
        evaluator = new CalinskiHarabasz();
        distanceMeasure = new EuclideanDistance();
    }

    @Test
    public void test_k_equals_4_is_best_for_a_4_center_points() {
        final UniformRandomProvider rnd = RandomSource.MT_64.create();
        final List<DoublePoint> points = createPointsAroundCenters(rnd);

        double bestScoreForAnyTestedK = 0.0;
        double scoreForFourCenters = 0.0;

        for (int clusterCountIndex = 0; clusterCountIndex < TESTED_CLUSTER_COUNTS; clusterCountIndex++) {
            final int clusterCount = MIN_CLUSTER_COUNT + clusterCountIndex;
            final double score = scoreForClusterCount(points, clusterCount, rnd);

            if (score > bestScoreForAnyTestedK) {
                bestScoreForAnyTestedK = score;
            }
            if (clusterCount == FOUR_CENTER_POINTS.length) {
                scoreForFourCenters = score;
            }
        }

        Assert.assertEquals(bestScoreForAnyTestedK, scoreForFourCenters, 0.0);
    }

    private List<DoublePoint> createPointsAroundCenters(final UniformRandomProvider rnd) {
        final List<DoublePoint> points = new ArrayList<>();

        for (int pointIndex = 0; pointIndex < POINT_COUNT; pointIndex++) {
            final double[] center = FOUR_CENTER_POINTS[pointIndex % FOUR_CENTER_POINTS.length];
            points.add(new DoublePoint(createPointNear(center, rnd)));
        }

        return points;
    }

    private double[] createPointNear(final double[] center, final UniformRandomProvider rnd) {
        final double[] point = new double[POINT_DIMENSION];

        for (int dimensionIndex = 0; dimensionIndex < POINT_DIMENSION; dimensionIndex++) {
            final double offset = (rnd.nextDouble() - 0.5) / 2;
            Assert.assertTrue(offset < MAX_OFFSET_FROM_CENTER && offset > -MAX_OFFSET_FROM_CENTER);
            point[dimensionIndex] = offset + center[dimensionIndex];
        }

        return point;
    }

    private double scoreForClusterCount(final List<DoublePoint> points,
                                        final int clusterCount,
                                        final UniformRandomProvider rnd) {
        final KMeansPlusPlusClusterer<DoublePoint> kMeans =
            new KMeansPlusPlusClusterer<>(clusterCount, Integer.MAX_VALUE, distanceMeasure, rnd);
        final List<CentroidCluster<DoublePoint>> clusters = kMeans.cluster(points);
        return evaluator.score(clusters);
    }
}
