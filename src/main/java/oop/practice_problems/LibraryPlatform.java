public class LibraryPlatform {
    interface Reservable { String reserve(String member); }
    interface Downloadable { String download(); }

    static abstract class Resource {
        final String id;
        Resource(String id) { this.id = id; }
    }
    static class Book extends Resource implements Reservable {
        Book(String id) { super(id); }
        public String reserve(String m) { return id + " reserved for " + m; }
    }
    static class EBook extends Resource implements Downloadable {
        EBook(String id) { super(id); }
        public String download() { return id + " downloaded"; }
    }

    private Resource[] items = new Resource[2];
    private int size = 0;

    /** Binary search by ID; returns index or -(insertionPoint)-1. */
    private int search(String id) {
        int lo = 0, hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int c = items[mid].id.compareTo(id);
            if (c == 0) return mid;
            if (c < 0) lo = mid + 1; else hi = mid - 1;
        }
        return -(lo + 1);
    }

    String add(Resource r) {
        int idx = search(r.id);
        if (idx >= 0) return "duplicate rejected";
        int pos = -(idx + 1);
        if (size == items.length) items = java.util.Arrays.copyOf(items, size * 2);
        System.arraycopy(items, pos, items, pos + 1, size - pos); // keep array ordered by ID
        items[pos] = r; size++;
        return r.id + " added";
    }

    String reserve(String id, String member) {
        int i = search(id);
        if (i < 0) return id + " not found";
        return items[i] instanceof Reservable ? ((Reservable) items[i]).reserve(member) : id + " rejected: reserve unsupported";
    }

    String download(String id) {
        int i = search(id);
        if (i < 0) return id + " not found";
        return items[i] instanceof Downloadable ? ((Downloadable) items[i]).download() : id + " rejected: download unsupported";
    }

    String find(String id) {
        int i = search(id);
        return i >= 0 ? id + " found at index " + i : id + " not found";
    }

    public static void main(String[] args) {
        LibraryPlatform lib = new LibraryPlatform();
        System.out.println(lib.add(new Book("B1")));
        System.out.println(lib.add(new Book("B1")));
        System.out.println(lib.add(new EBook("E1")));
        System.out.println(lib.reserve("B1", "M1"));
        System.out.println(lib.download("B1"));
        System.out.println(lib.download("E1"));
        System.out.println(lib.find("E1"));
    }
}
