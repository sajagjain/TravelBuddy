package com.sajagjain.sajagjain.majorproject;

/**
 * Created by sajag jain on 25-09-2017.
 */


public class ContactUsQuery{


    String emailid,askme;

    public ContactUsQuery(String emailid, String askme) {
        this.emailid = emailid;
        this.askme = askme;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAskme() {
        return askme;
    }

    public void setAskme(String askme) {
        this.askme = askme;
    }
}
