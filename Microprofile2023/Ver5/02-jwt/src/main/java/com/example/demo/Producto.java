package com.example.demo;
public class Producto{
    private Integer clave;
    private String desripcion;
    private Integer cantidad;
    
    public Producto(Integer clave, String desripcion, Integer cantidad) {
        this.clave = clave;
        this.desripcion = desripcion;
        this.cantidad = cantidad;
    }
    public void setClave(Integer clave) {
        this.clave = clave;
    }
    public void setDesripcion(String desripcion) {
        this.desripcion = desripcion;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public Integer getClave() {
        return clave;
    }
    public String getDesripcion() {
        return desripcion;
    }
    public Integer getCantidad() {
        return cantidad;
    }
  

}

