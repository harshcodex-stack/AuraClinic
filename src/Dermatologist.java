import java.util.UUID;

public class Dermatologist {
    private String id;
    private String name;

    public Dermatologist(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}
