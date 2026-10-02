import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.threeten.extra.chrono.DiscordianChronology;
import org.threeten.extra.chrono.DiscordianEra;

/**
 * Verifies {@link org.threeten.extra.chrono.DiscordianChronology#eraOf(int)}.
 * <p>
 * The Discordian calendar has a single era, YOLD ("Year of Our Lady of Discord"),
 * which is represented by era value 1. This test confirms that looking up era 1
 * returns that single era constant.
 */
public class TestDiscordianChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(DiscordianEra.YOLD, DiscordianChronology.INSTANCE.eraOf(1));
    }
}
