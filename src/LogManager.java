import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private static LogManager instance; //Singleton Pattern ให้มีเพียงหนึ่งอินสแตนซ์เท่านั้
    private List<String> logs;

    private LogManager() {
        logs = new ArrayList<>();
    }

    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
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