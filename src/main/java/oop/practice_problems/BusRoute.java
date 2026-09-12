class BusRoute {
    String routeCode;
    String routeName;
    int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 1); // default = lowest priority
    }

    // Higher priority ranks first; ties broken by routeCode, case-insensitive.
    int compareTo(BusRoute other) {
        if (this.priority != other.priority) {
            return other.priority - this.priority;
        }
        return this.routeCode.compareToIgnoreCase(other.routeCode);
    }

    static BusRoute[] rankRoutes(BusRoute[] routes) {
        BusRoute[] result = routes.clone();
        // stable insertion sort — equal elements keep their original relative order
        for (int i = 1; i < result.length; i++) {
            BusRoute key = result[i];
            int j = i - 1;
            while (j >= 0 && result[j].compareTo(key) > 0) {
                result[j + 1] = result[j];
                j--;
            }
            result[j + 1] = key;
        }
        return result;
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };
        for (BusRoute r : rankRoutes(routes)) System.out.println(r.routeCode);
    }
}