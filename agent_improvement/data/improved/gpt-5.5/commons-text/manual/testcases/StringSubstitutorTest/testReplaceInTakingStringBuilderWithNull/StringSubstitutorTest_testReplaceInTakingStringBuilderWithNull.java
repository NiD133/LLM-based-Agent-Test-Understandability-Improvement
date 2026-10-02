package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInTakingStringBuilderWithNull {

    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        final Map<String, Object> replacements = new HashMap<>();
        final StringSubstitutor substitutor = new StringSubstitutor(
                replacements,
                StringUtils.EMPTY,
                StringUtils.EMPTY,
                'T',
                "K+<'f");

        assertFalse(substitutor.replaceIn((StringBuilder) null));
    }
}
