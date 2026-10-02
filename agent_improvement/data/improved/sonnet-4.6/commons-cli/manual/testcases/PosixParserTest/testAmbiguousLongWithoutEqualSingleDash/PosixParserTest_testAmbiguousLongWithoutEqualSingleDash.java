package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PosixParser} covering the ambiguous-long-option-without-equals-single-dash
 * scenario (e.g. {@code -ver} when both {@code --version} and {@code --verbose} are defined).
 *
 * <p>PosixParser pre-dates the ambiguity-detection logic that was later introduced in
 * {@link DefaultParser}. When a single-dash token resembles a long option but matches more than
 * one defined option, PosixParser silently bursts the token into individual short-option characters
 * rather than throwing an {@link AmbiguousOptionException}. The test inherited from
 * {@link AbstractParserTestCase} therefore cannot pass and is explicitly disabled.
 */
public class PosixParserTest_testAmbiguousLongWithoutEqualSingleDash extends AbstractParserTestCase {

    /**
     * Initialises the shared {@code parser} field with a {@link PosixParser} instance so that
     * all inherited test helpers operate against the correct parser implementation.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled because {@link PosixParser} does not detect ambiguous long options supplied
     * with a single dash and no {@code =} separator — it falls back to character-by-character
     * bursting instead of throwing {@link AmbiguousOptionException}.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash() throws Exception {
    }
}
