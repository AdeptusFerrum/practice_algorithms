package kt6;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StationConnectionsTest {
    @Test
    void stationsStartIsolatedButConnectedToThemselves() {
        StationConnections stations = new StationConnections(3);
        assertTrue(stations.connected(1, 1));
        assertFalse(stations.connected(1, 2));
        assertFalse(stations.connected(1, 3));
    }

    @Test
    void connectionsAreBidirectionalAndTransitive() {
        StationConnections stations = new StationConnections(4);
        stations.connect(1, 2);
        stations.connect(2, 3);
        assertTrue(stations.connected(3, 1));
        assertFalse(stations.connected(1, 4));
    }

    @Test
    void repeatedAndSelfConnectionsDoNotChangeComponents() {
        StationConnections stations = new StationConnections(3);
        stations.connect(1, 2);
        stations.connect(2, 1);
        stations.connect(1, 1);
        assertTrue(stations.connected(1, 2));
        assertFalse(stations.connected(2, 3));
    }

    @Test
    void mergesComponentsOfDifferentSizes() {
        StationConnections stations = new StationConnections(6);
        stations.connect(1, 2);
        stations.connect(3, 4);
        stations.connect(3, 5);
        stations.connect(2, 4);
        assertTrue(stations.connected(1, 5));
        assertFalse(stations.connected(1, 6));
    }

    @Test
    void instancesAreIndependent() {
        StationConnections first = new StationConnections(2);
        StationConnections second = new StationConnections(2);
        first.connect(1, 2);
        assertFalse(second.connected(1, 2));
    }

    @Test
    void handlesMaximumStationCount() {
        StationConnections stations = new StationConnections(200_000);
        for (int i = 1; i < 200_000; i++) {
            stations.connect(i, i + 1);
        }
        assertTrue(stations.connected(1, 200_000));
    }
}
