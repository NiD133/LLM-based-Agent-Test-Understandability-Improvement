# JavaParser Test Block Detection

Generated at: `2026-09-24T03:43:10`
Data root: `/Users/tenghaha/Projects/Final_Agent_Understandability/data/improved`
JavaParser jar: `/Users/tenghaha/Projects/Final_Agent_Understandability/tools/javaparser/javaparser-core-3.27.0.jar`

Definition:

- Test methods are methods annotated with JUnit test annotations.
- A block is a contiguous group of non-blank source lines inside a test method body.
- Suite-level block count is the sum over all detected test methods in that Java file.

## Totals

| Metric | Count |
|---|---:|
| `pairs_analyzed` | 17190 |
| `original_test_methods` | 32279 |
| `improved_test_methods` | 32930 |
| `original_blocks` | 37762 |
| `improved_blocks` | 70591 |
| `pairs_with_more_blocks` | 12772 |
| `pairs_with_same_blocks` | 4274 |
| `pairs_with_fewer_blocks` | 144 |

## Largest Block Count Increases

| Test | Original | Improved | Delta | Methods original/improved |
|---|---:|---:|---:|---:|
| `opus-4.8/commons-text/auto/testsuites/StringSubstitutor_ESTest` | 62 | 161 | 99 | 56/56 |
| `opus-4.8/commons-cli/auto/testsuites/DefaultParser_ESTest` | 50 | 147 | 97 | 37/37 |
| `gpt-5.5/commons-cli/auto/testsuites/CommandLine_ESTest` | 54 | 147 | 93 | 51/51 |
| `opus-4.8/commons-text/auto/testsuites/StrSubstitutor_ESTest` | 46 | 131 | 85 | 41/41 |
| `opus-4.8/jsoup/auto/testsuites/Tag_ESTest` | 50 | 133 | 83 | 46/46 |
| `gpt-5.5/commons-text/auto/testsuites/StrSubstitutor_ESTest` | 46 | 117 | 71 | 41/41 |
| `opus-4.8/jackson-annotations/auto/testsuites/JsonAutoDetect_ESTest` | 44 | 113 | 69 | 40/40 |
| `opus-4.8/jackson-annotations/auto/testsuites/JsonFormat_ESTest` | 91 | 156 | 65 | 87/89 |
| `gpt-5.5/threeten-extra/auto/testsuites/AccountingChronology_ESTest` | 40 | 103 | 63 | 35/35 |
| `sonnet-4.6/threeten-extra/auto/testsuites/AccountingChronology_ESTest` | 40 | 103 | 63 | 35/35 |
| `gpt-5.5/threeten-extra/auto/testsuites/Days_ESTest` | 35 | 95 | 60 | 33/33 |
| `sonnet-4.6/jackson-annotations/auto/testsuites/JsonAutoDetect_ESTest` | 44 | 103 | 59 | 40/40 |
| `gpt-5.5/threeten-extra/auto/testsuites/Seconds_ESTest` | 37 | 94 | 57 | 33/33 |
| `opus-4.8/commons-lang/auto/testsuites/CharRange_ESTest` | 31 | 87 | 56 | 29/29 |
| `sonnet-4.6/jackson-annotations/auto/testsuites/JsonFormat_ESTest` | 91 | 147 | 56 | 87/87 |
| `sonnet-4.6/jackson-annotations/auto/testsuites/JsonSetter_ESTest` | 29 | 85 | 56 | 29/29 |
| `gpt-5.5/commons-lang/manual/testsuites/ArrayFillTest` | 28 | 83 | 55 | 28/28 |
| `gpt-5.5/threeten-extra/auto/testsuites/Weeks_ESTest` | 38 | 93 | 55 | 32/32 |
| `opus-4.8/commons-text/auto/testsuites/StringTokenizer_ESTest` | 42 | 96 | 54 | 36/36 |
| `gpt-5.5/commons-codec/auto/testsuites/MatchRatingApproachEncoder_ESTest` | 28 | 81 | 53 | 27/27 |
| `opus-4.8/commons-codec/auto/testsuites/MatchRatingApproachEncoder_ESTest` | 28 | 81 | 53 | 27/27 |
| `opus-4.8/threeten-extra/auto/testsuites/Minutes_ESTest` | 36 | 89 | 53 | 32/32 |
| `opus-4.8/threeten-extra/auto/testsuites/Seconds_ESTest` | 37 | 90 | 53 | 33/33 |
| `opus-4.8/threeten-extra/auto/testsuites/DiscordianChronology_ESTest` | 35 | 85 | 50 | 30/30 |
| `sonnet-4.6/commons-lang/manual/testsuites/EnumUtilsTest` | 61 | 111 | 50 | 48/48 |

## Largest Block Count Decreases

| Test | Original | Improved | Delta | Methods original/improved |
|---|---:|---:|---:|---:|
| `gpt-5.5/commons-cli/manual/testsuites/CommandLineTest` | 167 | 45 | -122 | 18/18 |
| `opus-4.8/commons-codec/manual/testsuites/MatchRatingApproachEncoderTest` | 96 | 13 | -83 | 96/13 |
| `gpt-5.5/commons-text/manual/testsuites/LevenshteinDetailedDistanceTest` | 73 | 29 | -44 | 17/17 |
| `gpt-5.5/commons-lang/manual/testsuites/CharSetTest` | 69 | 45 | -24 | 15/15 |
| `gpt-5.5/commons-lang/auto/testsuites/JavaVersion_ESTest` | 83 | 60 | -23 | 60/60 |
| `sonnet-4.6/commons-lang/auto/testsuites/JavaVersion_ESTest` | 83 | 60 | -23 | 60/60 |
| `opus-4.8/commons-text/manual/testsuites/LevenshteinDetailedDistanceTest` | 73 | 51 | -22 | 17/17 |
| `opus-4.8/jsoup/auto/testsuites/Validate_ESTest` | 57 | 36 | -21 | 36/36 |
| `sonnet-4.6/jsoup/auto/testsuites/Validate_ESTest` | 57 | 36 | -21 | 36/36 |
| `gpt-5.5/jsoup/auto/testsuites/Validate_ESTest` | 57 | 37 | -20 | 36/36 |
| `sonnet-4.6/commons-io/auto/testsuites/CircularByteBuffer_ESTest` | 50 | 34 | -16 | 31/31 |
| `gpt-5.5/commons-io/manual/testsuites/HexDumpTest` | 28 | 14 | -14 | 2/2 |
| `opus-4.8/commons-lang/auto/testsuites/JavaVersion_ESTest` | 83 | 70 | -13 | 60/60 |
| `sonnet-4.6/commons-cli/auto/testsuites/DefaultParser_ESTest` | 50 | 37 | -13 | 37/37 |
| `gpt-5.5/commons-cli/manual/testsuites/DefaultParserTest` | 35 | 24 | -11 | 11/11 |
| `gpt-5.5/commons-codec/manual/testsuites/Base16Test` | 60 | 49 | -11 | 31/31 |
| `gpt-5.5/commons-collections/manual/testsuites/BoundedIteratorTest` | 45 | 34 | -11 | 13/13 |
| `gpt-5.5/commons-text/manual/testsuites/LevenshteinDistanceTest` | 23 | 12 | -11 | 12/12 |
| `sonnet-4.6/commons-codec/auto/testsuites/Hex_ESTest` | 30 | 19 | -11 | 19/19 |
| `opus-4.8/commons-io/manual/testsuites/HexDumpTest` | 28 | 18 | -10 | 2/2 |
| `sonnet-4.6/commons-io/manual/testsuites/HexDumpTest` | 28 | 18 | -10 | 2/2 |
| `sonnet-4.6/spatial4j/auto/testsuites/SpatialContextFactory_ESTest` | 31 | 21 | -10 | 21/21 |
| `gpt-5.5/commons-text/manual/testsuites/StringSubstitutorTest` | 103 | 94 | -9 | 79/79 |
| `sonnet-4.6/commons-lang/auto/testsuites/RandomUtils_ESTest` | 32 | 23 | -9 | 22/22 |
| `sonnet-4.6/commons-math/manual/testsuites/LoessInterpolatorTest` | 48 | 39 | -9 | 22/22 |
