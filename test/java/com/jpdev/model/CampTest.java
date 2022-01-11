package com.jpdev.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampTest {

    private Camp camp;

    @BeforeEach
    public void Camp() {
        this.camp = new Camp(3, 3);
    }

    @Test
    public void testIfUnmarkedCampTurnMarked() {
        this.camp.toggleMarked();
        assertTrue(this.camp.isMarked());
    }

    @Test
    public void testIfMarkedCampTurnUnmarked() {
        this.camp.toggleMarked();
        this.camp.toggleMarked();
        assertFalse(this.camp.isMarked());
    }

    @Test
    public void testIfIsANeighbor() {
        Camp neighbor = new Camp(4, 4);
        assertTrue(this.camp.addNeighbor(neighbor));
    }

    @Test
    public void testOpenCamp() {
        Camp neighbor1 = new Camp(3, 2);
        Camp neighbor2 = new Camp(3, 1);
        Camp neighbor3 = new Camp(2, 2);
        Camp neighbor4 = new Camp(4, 4);
        Camp neighbor5 = new Camp(4, 5);

        this.camp.addNeighbor(neighbor1);
        this.camp.addNeighbor(neighbor2);
        this.camp.addNeighbor(neighbor3);
        this.camp.addNeighbor(neighbor4);
        this.camp.addNeighbor(neighbor5);

        assertTrue(this.camp.open());
    }
}