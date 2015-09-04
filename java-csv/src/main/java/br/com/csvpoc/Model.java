
package br.com.csvpoc;

public class Model {

    private String email;
    private String partner;

    public Model(String email, String partner) {
        this.email = email;
        this.partner = partner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

}
