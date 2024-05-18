package Entity;

public class AgentDetails {
    private int id;
    private String name;

    public AgentDetails(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void  setID(int id) { 
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}