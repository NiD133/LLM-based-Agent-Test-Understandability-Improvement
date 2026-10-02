import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.threeten.extra.chrono.Symmetry010Chronology;

/**
 * Tests that {@link Symmetry010Chronology} exposes exactly the two ISO eras (BCE and CE),
 * since the Symmetry010 calendar shares its era system with the Gregorian/ISO calendar.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_eras {

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = Symmetry010Chronology.INSTANCE.eras();

        assertEquals(2, eras.size(), "Symmetry010 should define exactly two eras");
        assertTrue(eras.contains(IsoEra.BCE), "eras should include BCE");
        assertTrue(eras.contains(IsoEra.CE), "eras should include CE");
    }
}
