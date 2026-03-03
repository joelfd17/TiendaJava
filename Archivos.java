package Tienda;

import java.io.*;
import java.util.Scanner;
import java.util.Date;

public class Archivos {

    public static void main(String[] args) {

        /* Tienda2026 t2026 = new Tienda2026();
        t2026.cargaDatos();
        t2026.guardaClientes();

        private void guardaClientes() {
            try (BufferedWriter bw = new BufferedWriter()){

            }
        } */


        /* Ejemplo 1
        File f = new File("archivo1.txt");

        try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Nombre: " + f.getName());
        System.out.println("Ruta: " + f.getAbsolutePath());
        System.out.println("Tamaño en Bytes: " + f.length());
        System.out.println("Fecha Última modificación: " + new Date(f.lastModified())); */


        /* Ejemplo 2
        Scanner sc = new Scanner(System.in);
        System.out.println("Archivo a eliminar: ");
        String nombre = sc.nextLine();
        File f = new File(nombre);
        System.out.println(f.getAbsolutePath());
        if (f.delete()){
            System.out.println("Archivo eliminado");
        } else{
            System.out.println("No se ha podido eliminar");
        }

        System.out.println("Nombre del Archivo a renombrar: ");
        nombre = sc.nextLine();
        File f1 = new File(nombre);
        System.out.println("Nuevo nombre? ");
        String nombre2 = sc.nextLine();
        File f2 = new File(nombre2);
        if (f1.renameTo(f2)){
            System.out.println("Se ha cambiado el nombre");
        } else{
            System.out.println("No se ha podido cambiar el nombre");
        } */


        // ESCRITURA en un archivo de texto (añadiendo líneas a las ya existentes)
        Scanner sc=new Scanner(System.in);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("archivo1.txt",true))) {
            String cadena;
            System.out.println("Teclea líneas de texto + RETORNO - (FIN para terminar)");
            cadena = sc.nextLine();
            while (!cadena.equalsIgnoreCase("FIN")) {
                bw.write(cadena);	//escribe la cadena en el BufferedWriter
                bw.newLine();	//añade un salto de línea
                cadena = sc.nextLine();	//Solicita una nueva cadena
            }
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el fichero");
        }

        try (Scanner scf = new Scanner(new File("archivo1.txt"))){
            while (scf.hasNextLine()) {
                System.out.println(scf.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
