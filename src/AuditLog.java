import java.util.ArrayList;
import java.util.List;

public class AuditLog {
    private static AuditLog instance; //Singleton Pattern ให้มีเพียงหนึ่งอินสแตนซ์เท่านั้น
    private List<String> logs; //รายการบันทึกเหตุการณ์

    private AuditLog() {
        logs = new ArrayList<>();
    }

    public static AuditLog getInstance() {
        if (instance == null) {
            instance = new AuditLog();
        }
        return instance;
    }

    public void logEvent(String event) {
        String timestamp = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        logs.add("[" + timestamp + "] " + event);
        System.out.println("บันทึก: " + event);
    }

    public void viewLogs() {
        System.out.println("\n--- บันทึกทั้งหมด ---");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}