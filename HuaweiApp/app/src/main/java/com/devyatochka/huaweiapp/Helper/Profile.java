package com.devyatochka.huaweiapp.Helper;

/**
 * Created by alexbelogurow on 28.03.17.
 */

public class Profile {
    private String fullName,
            login,
            password,
            numberPhone,
            modelPhone;

    private long id;

    public Profile(String fullName, String login, String password, String numberPhone, String modelPhone, long id) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.numberPhone = numberPhone;
        this.modelPhone = modelPhone;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Full Name: \'" + fullName + '\'' +
                "\nLogin: '" + login + '\'' +
                "\nPassword: '" + password + '\'' +
                "\nNumber Phone: '" + numberPhone + '\'' +
                "\nModel Phone: '" + modelPhone + "\'";
    }

    public String getFullName() {
        return fullName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getModelPhone() {
        return modelPhone;
    }

    public long getId() {
        return id;
    }
}
