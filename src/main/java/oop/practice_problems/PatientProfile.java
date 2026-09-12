class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPin;
    private boolean idSet = false; // tracks write-once state

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.patientId = null; // real ID must come through setPatientId
        this.name = name;
        this.discharged = false;
        if (patientId != null) {
            setPatientId(patientId);
        }
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (!idSet) {
            this.patientId = id;
            idSet = true;
        }
        // silently ignored on subsequent calls
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    // write-only: no getLockerPin() exists anywhere
    public void setLockerPin(String pin) {
        this.lockerPin = pin;
    }

    public static void main(String[] args) {
        System.out.println(new PatientProfile("Arjun Iyer").getPatientId());
        System.out.println(new PatientProfile("MT2026-0142", "Arjun Iyer").getPatientId());

        PatientProfile p = new PatientProfile();
        p.setPatientId("MT2026-0142");
        p.setPatientId("HACKED-0000");
        System.out.println(p.getPatientId());
    }
}