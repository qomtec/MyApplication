package com.qomtec.objects;

public class Usuario {
    private String usuario;
    private String clave;
    private String token;

    public Usuario() {
    }

    public Usuario(String usuario, String clave, String token) {
        this.usuario = usuario;
        this.clave = clave;
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Usuario{");
        sb.append("usuario='").append(usuario).append('\'');
        sb.append(", clave='").append(clave).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}