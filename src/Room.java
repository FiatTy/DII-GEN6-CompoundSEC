public class Room {
    private String roomId;
    private String floorLevel;

    public Room(String roomId, String floorLevel) {
        if (!isValidFloorLevel(floorLevel)) {
            throw new IllegalArgumentException("LOW, MEDIUM, HIGH เท่านั้น");
        }
        this.roomId = roomId;
        this.floorLevel = floorLevel.toUpperCase();
    }

    public String getRoomId() { return roomId; }
    public String getFloorLevel() { return floorLevel; }

    public void setFloorLevel(String floorLevel) {
        if (!isValidFloorLevel(floorLevel)) {
            throw new IllegalArgumentException("LOW, MEDIUM, HIGH เท่านั้น");
        }
        this.floorLevel = floorLevel.toUpperCase();
    }

    private boolean isValidFloorLevel(String floorLevel) {
        return floorLevel.equalsIgnoreCase("LOW") || floorLevel.equalsIgnoreCase("MEDIUM") || floorLevel.equalsIgnoreCase("HIGH");
    }
}