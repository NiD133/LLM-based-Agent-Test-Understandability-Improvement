package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Symmetry010Date_toEpochDay {

    /**
     * Pairs of (Symmetry010Date, equivalent ISO LocalDate) used to verify that
     * {@link Symmetry010Date#toEpochDay()} returns the same epoch-day value as
     * the corresponding ISO date. Historical figures and events are noted to
     * explain why each date was chosen as a representative sample.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // CE 1 January 1 — the calendar epoch: Sym010 and ISO agree
            { Symmetry010Date.of(1, 1, 1),         LocalDate.of(1, 1, 1)      },

            // Constantine the Great, Roman emperor (d. 337) — born ~272 AD
            { Symmetry010Date.of(272, 2, 28),      LocalDate.of(272, 2, 27)   },
            { Symmetry010Date.of(272, 2, 27),      LocalDate.of(272, 2, 26)   },

            // Charlemagne, Frankish king (d. 814) — born 742 AD
            { Symmetry010Date.of(742, 3, 27),      LocalDate.of(742, 4, 2)    },
            { Symmetry010Date.of(742, 4, 2),        LocalDate.of(742, 4, 7)   },

            // Norman Conquest: Battle of Hastings (1066-10-14)
            { Symmetry010Date.of(1066, 10, 14),    LocalDate.of(1066, 10, 14) },

            // Francesco Petrarca — Father of Humanism (d. 1374), born 1304
            { Symmetry010Date.of(1304, 7, 21),     LocalDate.of(1304, 7, 20)  },
            { Symmetry010Date.of(1304, 7, 20),     LocalDate.of(1304, 7, 19)  },

            // Charles the Bold, Burgundy (d. 1477) — born 1433
            { Symmetry010Date.of(1433, 11, 12),    LocalDate.of(1433, 11, 10) },
            { Symmetry010Date.of(1433, 11, 10),    LocalDate.of(1433, 11, 8)  },

            // Leonardo da Vinci, Italian polymath (d. 1519) — born 1452
            { Symmetry010Date.of(1452, 4, 11),     LocalDate.of(1452, 4, 15)  },
            { Symmetry010Date.of(1452, 4, 15),     LocalDate.of(1452, 4, 19)  },

            // Christopher Columbus makes landfall in the Caribbean (1492)
            { Symmetry010Date.of(1492, 10, 10),    LocalDate.of(1492, 10, 12) },
            { Symmetry010Date.of(1492, 10, 12),    LocalDate.of(1492, 10, 14) },

            // Galileo Galilei, Italian astronomer (d. 1642) — born 1564
            { Symmetry010Date.of(1564, 2, 18),     LocalDate.of(1564, 2, 15)  },
            { Symmetry010Date.of(1564, 2, 15),     LocalDate.of(1564, 2, 12)  },

            // William Shakespeare baptised in Stratford-upon-Avon (1564)
            { Symmetry010Date.of(1564, 4, 28),     LocalDate.of(1564, 4, 26)  },
            { Symmetry010Date.of(1564, 4, 26),     LocalDate.of(1564, 4, 24)  },

            // Sir Isaac Newton, English physicist (d. 1727) — born 1643
            { Symmetry010Date.of(1643, 1, 7),      LocalDate.of(1643, 1, 4)   },
            { Symmetry010Date.of(1643, 1, 4),      LocalDate.of(1643, 1, 1)   },

            // Leonhard Euler, Swiss mathematician (d. 1783) — born 1707
            { Symmetry010Date.of(1707, 4, 12),     LocalDate.of(1707, 4, 15)  },
            { Symmetry010Date.of(1707, 4, 15),     LocalDate.of(1707, 4, 18)  },

            // French Revolution: Citizens of Paris storm the Bastille (1789-07-14)
            { Symmetry010Date.of(1789, 7, 16),     LocalDate.of(1789, 7, 14)  },
            { Symmetry010Date.of(1789, 7, 14),     LocalDate.of(1789, 7, 12)  },

            // Albert Einstein, German theoretical physicist (d. 1955) — born 1879
            { Symmetry010Date.of(1879, 3, 14),     LocalDate.of(1879, 3, 14)  },

            // Dennis Ritchie, American computer scientist (d. 2011) — born 1941
            { Symmetry010Date.of(1941, 9, 11),     LocalDate.of(1941, 9, 9)   },
            { Symmetry010Date.of(1941, 9, 9),      LocalDate.of(1941, 9, 7)   },

            // Unix epoch: 1970-01-01 00:00:00 UTC
            { Symmetry010Date.of(1970, 1, 4),      LocalDate.of(1970, 1, 1)   },
            { Symmetry010Date.of(1970, 1, 1),      LocalDate.of(1969, 12, 29) },

            // Start of the 21st century / 3rd millennium (2000-01-01 ISO)
            { Symmetry010Date.of(1999, 12, 29),    LocalDate.of(2000, 1, 1)   },
            { Symmetry010Date.of(2000, 1, 1),      LocalDate.of(2000, 1, 3)   },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry010Date_toEpochDay(Symmetry010Date sym010, LocalDate iso) {
        assertEquals(iso.toEpochDay(), sym010.toEpochDay());
    }
}
