package com.devyatochka.huaweiapp.Helper;


public class RegistrationRequest {
    String full_name;
    String login;
    String password;
    String number_phone;
    String model_phone;

    public RegistrationRequest(String full_name, String login, String password, String number_phone, String model_phone) {
        this.full_name = full_name;
        this.login = login;
        this.password = password;
        this.number_phone = number_phone;
        this.model_phone = model_phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }

    public String getModel_phone() {
        return model_phone;
    }

    public void setModel_phone(String model_phone) {
        this.model_phone = model_phone;
    }
}
