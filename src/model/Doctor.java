package model;

public class Doctor {
    private String id;
    private String name;
    private String specialty;
    private int roomNo;

    public Doctor() {}

    public Doctor(String id, String name, String specialty, int roomNo) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.roomNo = roomNo;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public int getRoomNo() { return roomNo; }
    public void setRoomNo(int roomNo) { this.roomNo = roomNo; }

    @Override
    public String toString() {
        return String.format("Doctor[id=%s, name=%s, specialty=%s, room=%d]", id, name, specialty, roomNo);
    }
}
