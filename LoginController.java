public class LoginController {
    public boolean login(String username, String password) {
        return(new UserAccount().validateLogin(username, password));
    }

    public String validateProfile(String username) {
        return new UserAccount().validateProfile(username);
    }
}
