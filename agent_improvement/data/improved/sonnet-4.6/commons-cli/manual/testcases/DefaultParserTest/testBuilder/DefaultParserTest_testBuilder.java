package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.DefaultParser.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testBuilder extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Verifies that a {@link Builder} configured with non-default settings produces a
     * {@link DefaultParser} instance via both the deprecated {@code build()} method and
     * its replacement {@code get()}.
     */
    @Test
    void testBuilder() {
        final Builder builder = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(false)
                .setAllowPartialMatching(false)
                .setDeprecatedHandler(null);

        // build() is deprecated but must still return a DefaultParser
        parser = builder.build();
        assertEquals(DefaultParser.class, parser.getClass());

        // get() is the preferred replacement for build()
        parser = builder.get();
        assertEquals(DefaultParser.class, parser.getClass());
    }
}
