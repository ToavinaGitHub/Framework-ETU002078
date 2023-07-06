package model;

import etu2078.framework.ModelView;
import etu2078.framework.annotation.Url;
import etu2078.framework.annotation.Param;
import etu2078.framework.annotation.Scope;
import etu2078.framework.annotation.Authentification;
import java.util.Vector;
import java.util.Date;

@Scope(isSingleton=false)
public class Emp{
    Integer idEmp;
    String nomEmp;


    public Emp() {
    }

    public Integer getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Integer idEmp) {
        this.idEmp = idEmp;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    @Url(urlName="find")
    public ModelView test(@Param(paramAttribut="idEmp") int idEmp)
    {
        Vector<Object> tab = new Vector<Object>();


        ModelView m = new ModelView("haha.jsp");
        m.AddItem("lst",tab);


        return m;
    }

}
