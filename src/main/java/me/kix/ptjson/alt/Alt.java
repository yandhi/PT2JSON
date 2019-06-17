package me.kix.ptjson.alt;

/**
 * A container class for alts.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class Alt {

    /**
     * The login info of the alt.
     */
    private final String username, password;

    public Alt(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
