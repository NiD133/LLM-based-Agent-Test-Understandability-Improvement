package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * recognises two phonetic spellings of the same Slavic surname as a match.
 * <p>
 * The two inputs are alternative transliterations of the surname commonly written
 * as "Rosochowaciec" / "Rosokhovatsets". Although they are spelled differently,
 * the Match Rating Approach algorithm should encode them to homophonous codes and
 * therefore report them as equal.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_ROSOCHOWACIEC_ROSOKHOVATSETS_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    /** Polish-style spelling of the surname (digraphs "ch" and "ie"). */
    private static final String SURNAME_SPELLING_A = "R o s o ch o w a c ie c";

    /** Anglicised transliteration of the same surname (digraphs "kh", "ho" and "ts"). */
    private static final String SURNAME_SPELLING_B = " R o s o k ho v a ts e ts";

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surname_ROSOCHOWACIEC_ROSOKHOVATSETS_SuccessfullyMatched() {
        final boolean namesAreHomophonous =
                getStringEncoder().isEncodeEquals(SURNAME_SPELLING_A, SURNAME_SPELLING_B);

        assertTrue(namesAreHomophonous,
                "Two phonetic spellings of the same surname should be reported as matching");
    }
}
