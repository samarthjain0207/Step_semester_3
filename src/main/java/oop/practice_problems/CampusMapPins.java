public class CampusMapPins {

    static class CampusPin {
        public static String banner = "Campus";
        public String name = "General Pin";

        public String getDetails() {
            return "Details: " + name;
        }
    }

    static class HostelPin extends CampusPin {
        public static String banner = "Hostel";
        public String name = "Hostel Pin";

        @Override
        public String getDetails() {
            return "Details: " + name;
        }
    }

    public static void main(String[] args) {
        CampusPin p = new HostelPin();

        System.out.println("banner: " + p.banner + " (decided by reference type)");
        System.out.println("name: " + p.name + " (decided by reference type)");
        System.out.println(p.getDetails() + " (decided by object type)");

        System.out.println();
        System.out.println("banner: static members are hidden, not overridden -> resolved at compile time.");
        System.out.println("name: fields are hidden, not overridden -> resolved at compile time.");
        System.out.println("getDetails(): instance methods are overridden -> resolved at run time from the actual object;");
        System.out.println("inside HostelPin.getDetails(), name is HostelPin's own field.");
    }
}
