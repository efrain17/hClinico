package systems.altura.db.printer;

/**
 * Created by CarlosEfrain on 11/02/2016.
 */
public class DatosCuenta {
       private String nombre;
        private String direccion;

        public DatosCuenta(String nombre, String direccion){

            this.nombre = nombre;
            this.direccion = direccion;

        }
    //@Override
    //public String toString(){return nombre+","+direccion;}

        public void setNombre(String nombre){
            this.nombre = direccion;
        }
        public void setDireccion(String direccion){
        this.direccion = direccion;}

        public String getNombre(){return nombre;}
        public String getDireccion(){return direccion;}


}
