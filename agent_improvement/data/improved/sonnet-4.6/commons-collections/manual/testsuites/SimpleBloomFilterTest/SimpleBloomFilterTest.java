/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SimpleBloomFilter}.
 *
 * <p>This class inherits the full suite of standard Bloom filter tests from
 * {@link AbstractBloomFilterTest} and adds tests for behaviours that are specific
 * to {@code SimpleBloomFilter}'s internal bit-map representation, in particular
 * how the filter handles a {@link BitMapExtractor} that yields fewer bit-map
 * words than the filter's {@link Shape} requires.
 */
class SimpleBloomFilterTest extends AbstractBloomFilterTest<SimpleBloomFilter> {

    @Override
    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Verifies that merging a {@link BitMapExtractor} that produces fewer bit-map
     * words than the shape expects is handled gracefully.
     *
     * <p>The test shape ({@code Shape.fromKM(17, 72)}) requires two 64-bit words to
     * hold 72 bits, but the extractor below supplies only <em>one</em> word whose
     * value is {@code 2L} (binary {@code ...0010}), meaning exactly bit&nbsp;1 is
     * set. After the merge the filter should still report success and its cardinality
     * should reflect only that single set bit.
     */
    @Test
    void testMergeShortBitMapExtractor() {
        final SimpleBloomFilter filter = createEmptyFilter(getTestShape());

        // Supply one 64-bit word (bit 1 set = value 2) instead of the two words
        // that the 72-bit shape would normally require.
        final BitMapExtractor shortExtractor = consumer -> consumer.test(2L);

        assertTrue(filter.merge(shortExtractor),
                "merge() should return true even when the extractor supplies fewer words than the shape requires");
        assertEquals(1, filter.cardinality(),
                "Only bit 1 was set by the short extractor, so cardinality should be 1");
    }
}
