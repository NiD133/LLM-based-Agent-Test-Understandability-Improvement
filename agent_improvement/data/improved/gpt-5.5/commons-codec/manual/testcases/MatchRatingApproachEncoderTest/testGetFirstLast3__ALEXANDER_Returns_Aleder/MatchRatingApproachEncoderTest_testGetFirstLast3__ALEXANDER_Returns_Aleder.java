package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetFirstLast3__ALEXANDER_Returns_Aleder {

    private static final String NAME_LONGER_THAN_SIX_CHARACTERS = "Alexzander";
    private static final String FIRST_THREE_AND_LAST_THREE = "Aleder";

    private final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

    @Test
    final void getFirst3Last3ReturnsFirstThreeAndLastThreeCharactersForLongNames() {
        assertEquals(FIRST_THREE_AND_LAST_THREE,
                encoder.getFirst3Last3(NAME_LONGER_THAN_SIX_CHARACTERS));
    }
}
