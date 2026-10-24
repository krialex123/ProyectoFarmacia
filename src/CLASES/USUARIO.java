/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASES;

/**
 *
 * @author USUARIO
 */
public class USUARIO {
    
    private String DNI=null;
    private String NOMBRES=null;
    private String APELLIDOS=null;
    private String SEXO=null;
    private String DIRECCION=null;
    private String CELULAR=null;
    private String FECHA_NACIMIENTO=null;
    private String EDAD=null;
    private String ROLL=null;
    private String USUARIO=null;
    private String CONTRASENA=null;

    
    public String DatosCompleto()
    {
    return getDNI()+" "+getNOMBRES()+""+getAPELLIDOS()+""+getSEXO()+""+getDIRECCION()+""+getCEULAR()+""+getFECHA_NACIMIENTO()+""+getEDAD()+""+getROLL()+""+getUSUARIO()+""+getCONTRASENA();
    }
    /**
     * @return the DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * @param DNI the DNI to set
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     * @return the NOMBRES
     */
    public String getNOMBRES() {
        return NOMBRES;
    }

    /**
     * @param NOMBRES the NOMBRES to set
     */
    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    /**
     * @return the APELLIDOS
     */
    public String getAPELLIDOS() {
        return APELLIDOS;
    }

    /**
     * @param APELLIDOS the APELLIDOS to set
     */
    public void setAPELLIDOS(String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }

    /**
     * @return the SEXO
     */
    public String getSEXO() {
        return SEXO;
    }

    /**
     * @param SEXO the SEXO to set
     */
    public void setSEXO(String SEXO) {
        this.SEXO = SEXO;
    }

    /**
     * @return the DIRECCION
     */
    public String getDIRECCION() {
        return DIRECCION;
    }

    /**
     * @param DIRECCION the DIRECCION to set
     */
    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    /**
     * @return the CEULAR
     */
    public String getCEULAR() {
        return CELULAR;
    }

    /**
     * @param CELULAR the CELULAR to set
     */
    public void setCELULAR(String CELULAR) {
        this.CELULAR = CELULAR;
    }

    /**
     * @return the FECHA_NACIMIENTO
     */
    public String getFECHA_NACIMIENTO() {
        return FECHA_NACIMIENTO;
    }

    /**
     * @param FECHA_NACIMIENTO the FECHA_NACIMIENTO to set
     */
    public void setFECHA_NACIMIENTO(String FECHA_NACIMIENTO) {
        this.FECHA_NACIMIENTO = FECHA_NACIMIENTO;
    }

    /**
     * @return the EDAD
     */
    public String getEDAD() {
        return EDAD;
    }

    /**
     * @param EDAD the EDAD to set
     */
    public void setEDAD(String EDAD) {
        this.EDAD = EDAD;
    }

    /**
     * @return the ROLL
     */
    public String getROLL() {
        return ROLL;
    }

    /**
     * @param ROLL the ROLL to set
     */
    public void setROLL(String ROLL) {
        this.ROLL = ROLL;
    }

    /**
     * @return the USUARIO
     */
    public String getUSUARIO() {
        return USUARIO;
    }

    /**
     * @param USUARIO the USUARIO to set
     */
    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    /**
     * @return the CONTRASEÑA
     */
    public String getCONTRASENA() {
        return CONTRASENA;
    }

    /**
     * @param CONTRASEÑA the CONTRASEÑA to set
     */
    public void setCONTRASENA(String CONTRASENA) {
        this.CONTRASENA = CONTRASENA;
    }
    
}
