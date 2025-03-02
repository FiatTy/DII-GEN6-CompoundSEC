import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AccessCard {
    private String userName;
    private String cardId;
    private Set<String> accessibleFloors;
    private Set<String> accessibleRooms;
    private String startTime; // เวลาเริ่มต้นที่บัตรสามารถใช้งานได้
    private String endTime;   // เวลาสิ้นสุดที่บัตรสามารถใช้งานได้

    public AccessCard(String userName, Set<String> accessibleFloors, Set<String> accessibleRooms, String startTime, String endTime) {
        this.userName = userName;
        this.cardId = UUID.randomUUID().toString();
        this.accessibleFloors = accessibleFloors;
        this.accessibleRooms = accessibleRooms;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUserName() { return userName; }
    public String getCardId() { return cardId; }
    public Set<String> getAccessibleFloors() { return accessibleFloors; }
    public Set<String> getAccessibleRooms() { return accessibleRooms; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    public void setAccessibleFloors(Set<String> accessibleFloors) {
        this.accessibleFloors = accessibleFloors;
    }

    public void setAccessibleRooms(Set<String> accessibleRooms) {
        this.accessibleRooms = accessibleRooms;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}