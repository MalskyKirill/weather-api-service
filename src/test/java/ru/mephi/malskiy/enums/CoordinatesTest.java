package ru.mephi.malskiy.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    @Test
    void findByCodeReturnsMatchingCoordinate() {
        Coordinates moscow = Coordinates.findByCode("MSC");
        Coordinates spb = Coordinates.findByCode("SPB");

        assertNotNull(moscow);
        assertNotNull(spb);
        assertEquals("lat=55.75&lon=37.62", moscow.getLocation());
        assertEquals("lat=59.57&lon=30.19", spb.getLocation());
    }

    @Test
    void findByCodeReturnsNullWhenUnknown() {
        assertNull(Coordinates.findByCode("UNK"));
    }
}
