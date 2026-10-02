import org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.junit.Test;

public class LoessInterpolatorTest_testCompletelyIncorrectBandwidth2 {
    private static final double BANDWIDTH_ABOVE_ALLOWED_RANGE = 1.1;
    private static final int ROBUSTNESS_ITERATIONS = 3;

    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth2() {
        new LoessInterpolator(BANDWIDTH_ABOVE_ALLOWED_RANGE, ROBUSTNESS_ITERATIONS);
    }
}
