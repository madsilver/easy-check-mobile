package br.com.silver.easycheck.model;

import java.io.Serializable;

/**
 * Created by silver on 25/04/16.
 */
public class Invite implements Serializable {

    public String name;
    public String date;
    public String code;
    public int status;
    public String msg;

    public Invite(){

    }

    public Invite(InviteRequest ir){
        this.name = ir.name;
        this.status = ir.status;
        this.msg = ir.msg;
    }

    public Invite(String name, String date, String code, int status){
        this.name = name;
        this.date = date;
        this.code = code;
        this.status = status;
    }
}
