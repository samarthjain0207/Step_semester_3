class LibraryMemberBean {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;
    private boolean idSet = false;

    public LibraryMemberBean() {
        this(null, null);
    }

    public LibraryMemberBean(String name) {
        this(null, name);
    }

    public LibraryMemberBean(String membershipId, String name) {
        this.membershipId = null;
        this.name = name;
        this.premiumMember = false;
        if (membershipId != null) {
            setMembershipId(membershipId);
        }
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (!idSet) {
            this.membershipId = id;
            idSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // write-only — no getter exists anywhere
    public void setSecurityAnswer(String answer) {
        this.securityAnswer = answer; // could hash it; deterministic one-way transform is fine
    }

    public static void main(String[] args) {
        System.out.println(new LibraryMemberBean("Priya Nair").getMembershipId());
        System.out.println(new LibraryMemberBean("LIB-8841", "Priya Nair").getMembershipId());

        LibraryMemberBean m = new LibraryMemberBean();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000");
        System.out.println(m.getMembershipId());
    }
}