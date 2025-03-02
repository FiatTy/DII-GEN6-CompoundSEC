import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RoomManager {
    private Map<String, Room> rooms = new HashMap<>();
    private AuditLog auditLog = AuditLog.getInstance();

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public void roomMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- ระบบจัดการห้อง ---");
            System.out.println("1. เพิ่มห้อง");
            System.out.println("2. แก้ไขห้อง");
            System.out.println("3. ลบห้อง");
            System.out.println("4. แสดงห้องทั้งหมด");
            System.out.println("5. กลับไปเมนูหลัก");
            System.out.print("เลือกตัวเลือก: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addRoom(scanner);
                case 2 -> modifyRoom(scanner);
                case 3 -> removeRoom(scanner);
                case 4 -> showAllRooms();
                case 5 -> exit = true;
                default -> System.out.println("กรุณาเลือก 1-5.");
            }
        }
    }

    public void addRoom(Scanner scanner) {
        System.out.print("กรุณากรอก ID ห้อง: ");
        String roomId = scanner.next();
        System.out.print("กรุณากรอกชั้นของห้อง (LOW/MEDIUM/HIGH): ");
        String floorLevel = scanner.next().toUpperCase();

        if (!floorLevel.equals("LOW") && !floorLevel.equals("MEDIUM") && !floorLevel.equals("HIGH")) {
            System.out.println("ชั้นของห้องต้องเป็น LOW, MEDIUM หรือ HIGH เท่านั้น");
            return;
        }

        Room room = new Room(roomId, floorLevel);
        rooms.put(roomId, room);

        auditLog.logEvent("เพิ่มห้อง: " + roomId);
        System.out.println("เพิ่มห้องสำเร็จ: " + roomId);
    }

    public void modifyRoom(Scanner scanner) {
        System.out.print("กรุณากรอก ID ห้องที่ต้องการแก้ไข: ");
        String roomId = scanner.next();

        if (!rooms.containsKey(roomId)) {
            System.out.println("ไม่พบห้องนี้");
            return;
        }

        System.out.print("กรุณากรอกชั้นใหม่ของห้อง (LOW/MEDIUM/HIGH): ");
        String newFloorLevel = scanner.next().toUpperCase();

        if (!newFloorLevel.equals("LOW") && !newFloorLevel.equals("MEDIUM") && !newFloorLevel.equals("HIGH")) {
            System.out.println("ชั้นของห้องต้องเป็น LOW, MEDIUM หรือ HIGH เท่านั้น");
            return;
        }

        rooms.get(roomId).setFloorLevel(newFloorLevel);
        auditLog.logEvent("แก้ไขห้อง: " + roomId);
        System.out.println("แก้ไขห้องสำเร็จ: " + roomId);
    }

    public void removeRoom(Scanner scanner) {
        System.out.print("กรุณากรอก ID ห้องที่ต้องการลบ: ");
        String roomId = scanner.next();

        if (rooms.remove(roomId) != null) {
            auditLog.logEvent("ลบห้อง: " + roomId);
            System.out.println("ลบห้องสำเร็จ: " + roomId);
        } else {
            System.out.println("ไม่พบห้องนี้");
        }
    }

    public void showAllRooms() {
        System.out.println("\n--- รายการห้องทั้งหมด ---");
        if (rooms.isEmpty()) {
            System.out.println("ไม่มีห้องในระบบ");
        } else {
            for (Room room : rooms.values()) {
                System.out.println("ห้อง: " + room.getRoomId() + " | ชั้น: " + room.getFloorLevel());
            }
        }
    }
}