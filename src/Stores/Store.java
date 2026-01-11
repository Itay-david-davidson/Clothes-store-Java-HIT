package Stores;

public class Store {
    private final String id;
    private final String name;

    public Store(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

}
