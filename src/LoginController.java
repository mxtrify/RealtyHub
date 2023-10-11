public class LoginController {
    public boolean login(String username, String password) {
        return(new UserAccount().validateLogin(username, password));
    }
}
