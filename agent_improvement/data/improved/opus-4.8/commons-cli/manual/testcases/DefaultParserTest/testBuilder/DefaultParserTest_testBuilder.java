package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.DefaultParser.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link DefaultParser.Builder} produces {@link DefaultParser} instances.
 */
public class DefaultParserTest_testBuilder extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testBuilder() {
        // Configure a builder with every available option explicitly set.
        final Builder builder = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(false)
                .setAllowPartialMatching(false)
                .setDeprecatedHandler(null);

        // build() (deprecated) must return a DefaultParser instance.
        parser = builder.build();
        assertEquals(DefaultParser.class, parser.getClass());

        // get() (the replacement for build()) must return a DefaultParser instance too.
        parser = builder.get();
        assertEquals(DefaultParser.class, parser.getClass());
    }
}
