package util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger doctorSeq = new AtomicInteger(100);
    private static final AtomicInteger patientSeq = new AtomicInteger(1000);
    private static final AtomicInteger testSeq = new AtomicInteger(5000);

    public static String nextDoctorId() { return "D" + doctorSeq.getAndIncrement(); }
    public static String nextPatientId() { return "P" + patientSeq.getAndIncrement(); }
    public static String nextId(String prefix) {
        if (prefix == null) prefix = "X";
        switch (prefix) {
            case "D": return nextDoctorId();
            case "P": return nextPatientId();
            default: return prefix + testSeq.getAndIncrement();
        }
    }
    public static String nextTestId() { return "T" + testSeq.getAndIncrement(); }
}