import java.util.*;

public class CardManager {
    private Map<String, AccessCard> cards = new HashMap<>();
    private AuditLog auditLog = AuditLog.getInstance();

    public AccessCard getCardById(String cardId) {
        for (AccessCard card : cards.values()) {
            if (card.getCardId().equals(cardId)) {
                return card;
            }
        }
        return null;
    }

    public void cardMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- ระบบจัดการบัตร ---");
            System.out.println("1. เพิ่มบัตร");
            System.out.println("2. แก้ไขบัตร");
            System.out.println("3. ลบบัตร");
            System.out.println("4. แสดงบัตรทั้งหมด");
            System.out.println("5. กลับไปเมนูหลัก");
            System.out.print("เลือกตัวเลือก: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addCard(scanner);
                case 2 -> modifyCard(scanner);
                case 3 -> removeCard(scanner);
                case 4 -> showAllCards();
                case 5 -> exit = true;
                default -> System.out.println("กรุณาเลือก 1-5.");
            }
        }
    }

    public void addCard(Scanner scanner) {
        System.out.print("ชื่อผู้ใช้: ");
        String userName = scanner.next();

        Set<String> accessibleFloors = getValidFloors(scanner);
        Set<String> accessibleRooms = getValidRooms(scanner);

        System.out.print("กรุณากรอกเวลาเริ่มต้นที่บัตรสามารถใช้งานได้ (HH:mm): ");
        String startTime = scanner.next();
        System.out.print("กรุณากรอกเวลาสิ้นสุดที่บัตรสามารถใช้งานได้ (HH:mm): ");
        String endTime = scanner.next();

        AccessCard card = new AccessCard(userName, accessibleFloors, accessibleRooms, startTime, endTime);
        cards.put(userName, card);

        String timestamp = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        auditLog.logEvent("[กดเพิ่มบัตรตอน " + timestamp + "] เพิ่มบัตรสำหรับ: " + userName + " | ID: " + card.getCardId());
        System.out.println("เพิ่มบัตรสำเร็จ: " + userName + " | ID บัตร: " + card.getCardId());
    }

    public void modifyCard(Scanner scanner) {
        System.out.print("กรุณากรอก ID บัตรที่ต้องการแก้ไข: ");
        String cardId = scanner.next();

        AccessCard card = getCardById(cardId);
        if (card == null) {
            System.out.println("ไม่พบบัตรนี้");
            return;
        }

        Set<String> newAccessibleFloors = getValidFloors(scanner);
        Set<String> newAccessibleRooms = getValidRooms(scanner);

        System.out.print("กรุณากรอกเวลาเริ่มต้นที่บัตรสามารถใช้งานได้ (HH:mm): ");
        String startTime = scanner.next();
        System.out.print("กรุณากรอกเวลาสิ้นสุดที่บัตรสามารถใช้งานได้ (HH:mm): ");
        String endTime = scanner.next();

        card.setAccessibleFloors(newAccessibleFloors);
        card.setAccessibleRooms(newAccessibleRooms);
        card.setStartTime(startTime);
        card.setEndTime(endTime);

        String timestamp = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        auditLog.logEvent("[กดแก้ไขบัตรตอน " + timestamp + "] แก้ไขบัตร: " + cardId);
        System.out.println("แก้ไขบัตรสำเร็จ: " + cardId);
    }

    public void removeCard(Scanner scanner) {
        System.out.print("กรุณากรอก ID บัตรที่ต้องการลบ: ");
        String cardId = scanner.next();

        AccessCard card = getCardById(cardId);
        if (card == null) {
            System.out.println("ไม่พบบัตรนี้");
            return;
        }

        cards.values().removeIf(c -> c.getCardId().equals(cardId));
        String timestamp = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        auditLog.logEvent("[กดลบบัตรตอน " + timestamp + "] ลบบัตร: " + cardId);
        System.out.println("ลบบัตรสำเร็จ: " + cardId);
    }

    public void showAllCards() {
        System.out.println("\n--- รายการบัตรทั้งหมด ---");
        for (AccessCard card : cards.values()) {
            System.out.println("บัตรของ: " + card.getUserName() + " | ID: " + card.getCardId() +
                    " | ชั้นที่เข้าถึงได้: " + card.getAccessibleFloors() +
                    " | ห้องที่เข้าถึงได้: " + card.getAccessibleRooms() +
                    " | เวลาใช้งานได้: " + card.getStartTime() + " - " + card.getEndTime());
        }
    }

    private Set<String> getValidFloors(Scanner scanner) {
        Set<String> floors = new HashSet<>();
        System.out.println("กรุณากรอกชั้นที่เข้าถึงได้ (LOW/MEDIUM/HIGH) สูงสุด 3 ชั้น (พิมพ์ STOP เพื่อหยุด):");
        while (floors.size() < 3) {
            String floor = scanner.next();
            if (floor.equalsIgnoreCase("STOP")) break;
            if (floor.equalsIgnoreCase("LOW") || floor.equalsIgnoreCase("MEDIUM") || floor.equalsIgnoreCase("HIGH")) {
                floors.add(floor.toUpperCase());
            } else {
                System.out.println("กรุณาเลือกเฉพาะ LOW, MEDIUM หรือ HIGH เท่านั้น");
            }
        }
        return floors;
    }

    private Set<String> getValidRooms(Scanner scanner) {
        Set<String> rooms = new HashSet<>();
        System.out.println("กรุณากรอก ID ห้องที่เข้าถึงได้ (พิมพ์ STOP เพื่อหยุด):");
        while (true) {
            String roomId = scanner.next();
            if (roomId.equalsIgnoreCase("STOP")) break;
            rooms.add(roomId);
        }
        return rooms;
    }

    // ตรวจสอบเวลา
    public boolean isCardValid(AccessCard card) {
        String currentTime = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
        return currentTime.compareTo(card.getStartTime()) >= 0 && currentTime.compareTo(card.getEndTime()) <= 0;
    }
}