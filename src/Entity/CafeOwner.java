package Entity;

public class CafeOwner extends UserAccount {
    public CafeOwner() {
        super();
    }

    public CafeOwner(String username, String password) {
        super(username, password);
    }

    public CafeOwner(String username, String password, String firstName, String lastName, String email, UserProfile userProfile) {
        super(username, password, firstName, lastName, email, userProfile);
    }

    public CafeOwner(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, boolean status) {
        super(username, password, firstName, lastName, email, userProfile, status);
    }
}
