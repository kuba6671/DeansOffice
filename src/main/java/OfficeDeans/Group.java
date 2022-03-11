package OfficeDeans;

public class Group {
    private int GroupID;
    private String name;

    public Group(int groupID, String name) {
        GroupID = groupID;
        this.name = name;
    }

    public int getGroupID() {
        return GroupID;
    }

    public String getName() {
        return name;
    }
}
