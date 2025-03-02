import java.util.Scanner;

public class Main {
    private static final CardManager cardManager = new CardManager();
    private static final RoomManager roomManager = new RoomManager();
    private static final AuditLog auditLog = AuditLog.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n COMPOUND SECURITY SYSTEM ");
            System.out.println("1. จัดการห้อง");
            System.out.println("2. จัดการบัตร");
            System.out.println("3. ตรวจสอบสิทธิ์เข้าห้อง");
            System.out.println("4. ดูบันทึก");
            System.out.println("5. ออกจากระบบ");
            System.out.print("กรุณาเลือก: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> roomManager.roomMenu(scanner);
                case 2 -> cardManager.cardMenu(scanner);
                case 3 -> checkAccess();
                case 4 -> auditLog.viewLogs();
                case 5 -> exit = true;
                default -> System.out.println("กรุณาเลือก 1-5.");
            }
        }
        scanner.close();
    }

    private static void checkAccess() {
        System.out.print("กรุณากรอก ID บัตร: ");
        String cardId = scanner.next();

        AccessCard card = cardManager.getCardById(cardId);

        if (card == null) {
            auditLog.logEvent("การเข้าถึงล้มเหลว: ไม่พบบัตร ID " + cardId);
            System.out.println("ไม่พบบัตรนี้");
            return;
        }

        // ตรวจสอบเวลา
        if (!cardManager.isCardValid(card)) {
            auditLog.logEvent("การเข้าถึงล้มเหลว: บัตร " + cardId + " ไม่อยู่ในช่วงเวลาที่ใช้งานได้");
            System.out.println("บัตรนี้ไม่อยู่ในช่วงเวลาที่ใช้งานได้");
            return;
        }

        System.out.print("กรุณากรอกชั้นที่ต้องการเข้า (LOW/MEDIUM/HIGH): ");
        String floorLevel = scanner.next().toUpperCase();

        if (!floorLevel.equals("LOW") && !floorLevel.equals("MEDIUM") && !floorLevel.equals("HIGH")) {
            System.out.println("คุณสามารถเข้าถึงได้เฉพาะชั้น LOW, MEDIUM หรือ HIGH เท่านั้น");
            return;
        }

        if (!card.getAccessibleFloors().contains(floorLevel)) {
            auditLog.logEvent("การเข้าถึงล้มเหลว: บัตร " + cardId + " ไม่มีสิทธิ์เข้าชั้น " + floorLevel);
            System.out.println("คุณไม่มีสิทธิ์เข้าถึงชั้นนี้");
            return;
        }

        System.out.print("กรุณากรอก ID ห้องที่ต้องการเข้า: ");
        String roomId = scanner.next();
        Room room = roomManager.getRoom(roomId);

        if (room == null) {
            auditLog.logEvent("การเข้าถึงล้มเหลว: ไม่พบห้อง ID " + roomId);
            System.out.println("ไม่พบห้องนี้");
            return;
        }

        if (!card.getAccessibleRooms().contains(roomId)) {
            auditLog.logEvent("การเข้าถึงล้มเหลว: บัตร " + cardId + " ไม่มีสิทธิ์เข้าห้อง " + roomId);
            System.out.println("คุณไม่มีสิทธิ์เข้าห้องนี้");
        } else {
            auditLog.logEvent("การเข้าถึงสำเร็จ: บัตร " + cardId + " เข้าห้อง " + roomId);
            System.out.println("คุณสามารถเข้าห้อง " + roomId + " ได้");
        }
    }
}