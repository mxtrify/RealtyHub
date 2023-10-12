public class LoginController {
    // Checking username and password
    public boolean login(String username, String password) {
        // Return true if match
        return(new UserAccount().validateLogin(username, password));
    }

    // Checking profile
    public String validateProfile(String username) {
        // Return the profile name
        return new UserAccount().validateProfile(username);
    }
}
