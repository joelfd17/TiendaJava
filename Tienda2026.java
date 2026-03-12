package Tienda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;

import java.time.LocalDate;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Comparator;

import java.util.stream.Collectors;

public class Tienda2026 {

    private Scanner sc=new Scanner(System.in);
    private ArrayList<Pedido> pedidos;
    private HashMap <String, Articulo> articulos;
    private HashMap <String, Cliente> clientes;

    public Tienda2026() {
        pedidos = new ArrayList<>();
        articulos= new HashMap();
        clientes = new HashMap();
    }

    public static void main(String[] args) {
        Tienda2026 t2026=new Tienda2026();
        t2026.cargaDatos();
        t2026.menu();
    }

    public void cargaDatos() {
        clientes.put("80580845T", new Cliente("80580845T", "ANA ", "658111111", "ana@gmail.com"));
        clientes.put("36347775R", new Cliente("36347775R", "LOLA", "649222222", "lola@gmail.com"));
        clientes.put("63921307Y", new Cliente("63921307Y", "JUAN", "652333333", "juan@gmail.com"));
        clientes.put("02337565Y", new Cliente("02337565Y", "EDU", "634567890", "edu@gmail.com"));

        articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH ST ", 0, 15));
        articulos.put("1-22", new Articulo("1-22", "TECLADO STANDARD  ", 5, 18));
        articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1 TB  ", 15, 80));
        articulos.put("2-22", new Articulo("2-22", "SSD KINGSTOM 256GB", 9, 70));
        articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 0, 200));
        articulos.put("3-11", new Articulo("3-11", "HP LASERJET HP800 ", 2, 200));
        articulos.put("3-22", new Articulo("3-22", "EPSON PRINT XP300 ", 5, 80));
        articulos.put("4-11", new Articulo("4-11", "ASUS  MONITOR  22 ", 5, 100));
        articulos.put("4-22", new Articulo("4-22", "HP MONITOR LED 28 ", 5, 180));
        articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY G5", 12, 580));

        LocalDate hoy = LocalDate.now();
        pedidos.add(new Pedido("80580845T-001/2025", clientes.get("80580845T"), hoy.minusDays(1), new ArrayList<>(List.of(new LineaPedido(articulos.get("1-11"), 3), new LineaPedido(articulos.get("4-22"), 3)))));
        pedidos.add(new Pedido("80580845T-002/2025", clientes.get("80580845T"), hoy.minusDays(2), new ArrayList<>(List.of(new LineaPedido(articulos.get("4-11"), 3), new LineaPedido(articulos.get("4-22"), 2), new LineaPedido(articulos.get("4-33"), 4)))));
        pedidos.add(new Pedido("36347775R-001/2025", clientes.get("36347775R"), hoy.minusDays(3), new ArrayList<>(List.of(new LineaPedido(articulos.get("4-22"), 1), new LineaPedido(articulos.get("2-22"), 3)))));
        pedidos.add(new Pedido("36347775R-002/2025", clientes.get("36347775R"), hoy.minusDays(5), new ArrayList<>(List.of(new LineaPedido(articulos.get("4-33"), 3), new LineaPedido(articulos.get("2-11"), 3)))));
        pedidos.add(new Pedido("63921307Y-001/2025", clientes.get("63921307Y"), hoy.minusDays(4), new ArrayList<>(List.of(new LineaPedido(articulos.get("2-11"), 5), new LineaPedido(articulos.get("2-33"), 3), new LineaPedido(articulos.get("4-33"), 2)))));
    }

    private void altaArticulos() {

        String idArticulo,descripcion,existencias,pvp; // TODAS LAS ENTRADAS COMO STRING FACILITA VALIDACION Y EVITA PROBLEMAS CON SCANNER

        System.out.println("ALTA DE NUEVO ARTICULO");
        //idArticulo VALIDADO CON EXPRESION REGULAR SENCILLA
        do{
            System.out.println("IdArticulo (IDENTIFICADOR):");
            idArticulo=sc.nextLine();
        }while (!idArticulo.matches("[1-6][-][0-9][0-9]") || articulos.containsKey(idArticulo));
        //OJO CONTROLAR tambien QUE NO EXISTA ESE ID PREVIAMENTE

        //ENTRADA DESCRIPCION SIN NINGUN TIPO DE VALIDACION
        System.out.println("DESCRIPCION:");
        descripcion=sc.nextLine();

        // EXISTENCIAS CON VALIDACION DE TIPO int
        do {
            System.out.println("EXISTENCIAS:");
            existencias=sc.nextLine(); //Se lee la entrada de EXISTENCIAS como un String
        } while(!MetodosAux.esInt(existencias)); //Se sigue pidiendo la entrada si no es int

        // PVP CON VALIDACION DE TIPO double
        do {
            System.out.println("PVP:");
            pvp=sc.nextLine(); //Se lee la entrada del PVP como un String
        } while(!MetodosAux.esDouble(pvp)); //Se sigue pidiendo la entrada si no es double

        //AÑADO OBJETO ARTICULO A LA COLECCION PARSEANDO A int y double los datos de existencias y PVP

        Articulo a= new Articulo (idArticulo,descripcion,
                Integer.parseInt(existencias),Double.parseDouble(pvp));
        articulos.put (idArticulo,a);
        System.out.println("- Articulo añadido -");
        /* por supuesto podría haberlo hecho con una única istrucción
        articulos.put(idArticulo,new Articulo(idArticulo,descripcion,Integer.parseInt(existencias),Double.parseDouble(pvp)));
        */
    }

    public String generaIdPedido(String idCliente){
        String nuevoId; //vble String para ir construyendo un nuevo idPedido
        //Calculamos en la vble contador cuantos pedidos tiene el cliente aportado
        int contador = 0;
        for (Pedido p: pedidos){
            if (p.getClientePedido().getIdCliente().equalsIgnoreCase(idCliente)){
                contador++;
            }
        }
        contador++; //sumamos a contador 1 para el nuevo pedido
        nuevoId= idCliente + "-" + String.format("%03d", contador) + "/" + LocalDate.now().getYear();
        return nuevoId;
    }

    public double totalCliente(String idCliente) {

        double total = 0;

        for (Pedido p : pedidos) {
            if (p.getClientePedido().getIdCliente().equals(idCliente)) {
                total += p.totalPedido();
            }
        }

        return total;
    }

    public void stock(Articulo articulo, int unidades)
            throws StockCero, StockInsuficiente {

        if (articulo.getExistencias() == 0) {
            throw new StockCero("Stock a cero");
        }

        if (articulo.getExistencias() < unidades) {
            throw new StockInsuficiente("Stock insuficiente");
        }
    }

    private void nuevoPedido() {

        String idCliente;
        do{
            System.out.println("DNI (id) CLIENTE:");
            idCliente=sc.nextLine().toUpperCase();
            if (!clientes.containsKey(idCliente)){
                System.out.println("No es cliente de la tienda."
                        + " Desea darse de alta o compra como invitado");
            }
        }while (!MetodosAux.validarDNI(idCliente));

        ArrayList <LineaPedido> cestaCompra =new ArrayList();
        String idArticulo;
        int unidades=0;
        System.out.print("\nTecle el ID del artículo deseado (FIN para terminar la compra)");
        idArticulo=sc.next();
        while (!idArticulo.equalsIgnoreCase("FIN")){
            System.out.print("\nTeclea las unidades deseadas: ");
            unidades=sc.nextInt();

            try {
                stock(articulos.get(idArticulo), unidades);
                cestaCompra.add(new LineaPedido(articulos.get(idArticulo), unidades));
            } catch (StockCero ex) {
                System.out.println(ex.getMessage());
            } catch (StockInsuficiente ex) {
                System.out.println(ex.getMessage());
                System.out.println("Las quieres (SI/NO)");
                String respuesta=sc.next();
                if (respuesta.equalsIgnoreCase("SI")){
                    cestaCompra.add(new LineaPedido(articulos.get(idArticulo),
                            articulos.get(idArticulo).getExistencias()));
                }
            }
            System.out.print("\nTecle el ID del artículo deseado (FIN para terminar la compra)");
            idArticulo=sc.next();
        }

        if (!cestaCompra.isEmpty()){
            System.out.println("Este es tu pedido");
            for (LineaPedido l:cestaCompra){
                System.out.println( l.getArticulo().getIdArticulo()+"-"+
                        articulos.get(l.getArticulo().getIdArticulo()).getDescripcion() + " - " + l.getUnidades() );
            }
            System.out.println("Procedemos con la compra (SI/NO) ");
            String respuesta=sc.next();
            if (respuesta.equalsIgnoreCase("SI")){
                pedidos.add(new Pedido(generaIdPedido(idCliente), clientes.get(idCliente),
                        LocalDate.now(), cestaCompra));
                for (LineaPedido l:cestaCompra){
                    articulos.get(l.getArticulo().getIdArticulo())
                            .setExistencias(articulos.get(l.getArticulo().getIdArticulo()).getExistencias()-l.getUnidades());
                }
            }
        }
    }

    public void menu() {

        int opcion;

        do {
            System.out.println("\n========== MENU TIENDA ==========");
            System.out.println("1. Alta de artículo");
            System.out.println("2. Nuevo pedido");
            System.out.println("3. Listado de artículos");
            System.out.println("4. Listado de pedidos");
            System.out.println("5. Ver ejemplos de Streams");

            System.out.println("\n----- ARCHIVOS CLIENTES -----");
            System.out.println("6. Guardar clientes en TXT y CSV");
            System.out.println("7. Leer clientes desde archivos");

            System.out.println("\n----- ARCHIVOS ARTICULOS -----");
            System.out.println("8. Guardar artículos por sección (CSV)");
            System.out.println("9. Leer artículos por sección");

            System.out.println("\n----- COPIA DE SEGURIDAD -----");
            System.out.println("10. Exportar colecciones (.dat)");
            System.out.println("11. Importar colecciones (.dat)");

            System.out.println("\n0. Salir");
            System.out.print("Seleccione opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1 -> altaArticulos();

                case 2 -> nuevoPedido();

                case 3 -> {
                    System.out.println("\nLISTADO DE ARTICULOS");
                    articulos.values().forEach(System.out::println);
                }

                case 4 -> {
                    System.out.println("\nLISTADO DE PEDIDOS");
                    pedidos.forEach(System.out::println);
                }

                case 5 -> ejemplosVarios();

                case 6 -> guardaClientes();

                case 7 -> leeClientes();

                case 8 -> guardaArticulosPorSeccion();

                case 9 -> leeArticulosPorSeccion();

                case 10 -> ExportarColecciones();

                case 11 -> importarColecciones();

                case 0 -> System.out.println("Saliendo del programa...");

                default -> System.out.println("Opción no válida");

            }

        } while (opcion != 0);
    }

    private void ejemplosVarios(){
        //EJEMPLO 1
        //CREAR UNA LISTA DE PEDIDOS ORDENADOS POR FECHA
        List <Pedido> pedidosOrdenadosFecha
                = pedidos.stream()
                .sorted(Comparator.comparing(Pedido::getFechaPedido))
                .collect(Collectors.toList());

        //COMPROBACIÓN DE RESULTADOS, LISTA ORIGINAL pedidos Y LISTA ORDENADA  pedidosOrdenadosFecha
        pedidos.stream().forEach(p->System.out.println(p.getIdPedido()+" - "+p.getFechaPedido()));
        System.out.println("\n");
        pedidosOrdenadosFecha.stream().forEach(p->System.out.println(p.getIdPedido()+" - "+p.getFechaPedido()));


        //EJEMPLO 2
        //CREAR UN MAPA ORDENADO (TreeMap) CON LOS PEDIDOS (values) Y EL TOTAL DE CADA PEDIDO (key) ORDENADOS POR FECHA
        TreeMap <Double,Pedido> pedidosConTotales=new TreeMap();
        for (Pedido p:pedidos){
            pedidosConTotales.put(p.totalPedido(), p);
        }
        //COMPROBACIÓN DE RESULTADOS .keySet mostrará de < a > los totales
        // .descendingKeySet() los mostrará de > a <
        System.out.println("\n");
        for (Double totPedido:pedidosConTotales.descendingKeySet()){
            System.out.println(pedidosConTotales.get(totPedido).getIdPedido() + " - " + totPedido);
        }

        //EJEMPLO 3
        //CREAR UN MAPA ORDENADO (TreeMap) CON LOS CLIENTES (values) Y EL TOTAL GASTADO POR CADA CLIENTE
        TreeMap<Double, Cliente> ventasPorCliente=new TreeMap();
        for (Cliente c:clientes.values()){
            ventasPorCliente.put(totalCliente(c.getIdCliente()),c);
        }

        //COMPROBACIÓN DE RESULTADOS .keySet mostrará de < a > los totales
        // .descendingKeySet() los mostrará de > a <
        System.out.println("\n");
        for (Double totCli:ventasPorCliente.keySet()){
            System.out.println(ventasPorCliente.get(totCli).getNombre()+ " - " + totCli);
        }

        //EJEMPLO 4
        //CREAR UNA COLECCION DE TIPO List CON LOS ARTICULOS DE CADA SECCION
        List <Articulo> perifericos, almacenamiento, monitores, impresoras;

        // CON STREAMS y .collect
        perifericos=articulos.values().stream()
                .filter(a->a.getIdArticulo().startsWith("1"))
                .collect(Collectors.toList());
        almacenamiento=articulos.values().stream()
                .filter(a->a.getIdArticulo().startsWith("2"))
                .collect(Collectors.toList());
        impresoras=articulos.values().stream()
                .filter(a->a.getIdArticulo().startsWith("3"))
                .collect(Collectors.toList());
        monitores=articulos.values().stream()
                .filter(a->a.getIdArticulo().startsWith("4"))
                .collect(Collectors.toList());

        //ESTILO CLÁSICO CON UN switch y .add
        for (Articulo a : articulos.values()) {
            switch (a.getIdArticulo().charAt(0)) {
                case '1':
                    perifericos.add(a);
                    break;
                case '2':
                    almacenamiento.add(a);
                    break;
                case '3':
                    impresoras.add(a);
                    break;
                case '4':
                    monitores.add(a);
                    break;
            }
        }
        //COMPROBACIÓN DE RESULTADOS
        System.out.println("\n" + perifericos);
        System.out.println("\n" + almacenamiento);
        System.out.println("\n" + impresoras);
        System.out.println("\n" + monitores);

        //EJEMPLO 5 - BORRADO EN COLECCIONES

        //BORRAR LOS ARTICULOS DE LA SECCIÓN IMPRESORAS
        articulos.values().removeIf(a->a.getIdArticulo().startsWith("3"));
        System.out.println("\n");
        articulos.values().stream()
                .forEach(a->System.out.println(a));



        //BORRAR LOS PEDIDOS DE MÁS DE 3 DÍAS DE ANTIGUEDAD
        //Las colecciones de tipo List no admiten removeIf()
        List <Pedido> pedidosAntiguos=pedidos.stream()
                .filter(p->p.getFechaPedido().isBefore(LocalDate.now().minusDays(3)))
                .collect(Collectors.toList());
        pedidos.removeAll(pedidosAntiguos);

        System.out.println(pedidos);

    }

    private void guardaClientes(){
        //GUARDAMOS LOS CLIENTES LÍNEA A LÍNEA EN UN ARCHIVO .txt ESCRIBIENDO LOS DATOS SEGUN TENGAMOS DISPUESTO EN EL toString()
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/clientes.txt"))) {
            for (Cliente c:clientes.values()){
                bw.write(c.toString());
                bw.newLine();
            }
            System.out.println("Archivo D:/clientes.txt creado correctamente");
        } catch (IOException e) {
            System.out.println("No se ha podido crear el archivo D:/clientes.txt");
        }

        //GUARDAMOS LOS CLIENTES LÍNEA A LÍNEA EN UN ARCHIVO .csv CON LOS VALORES DE LOS ATRIBUTOS SEPARADOS POR ,

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/clientes.csv"))) {
            for (Cliente c:clientes.values()){
                bw.write(c.getIdCliente()+","+c.getNombre()+","+c.getTelefono()+","+c.getEmail()+"\n");
            }
            System.out.println("Archivo D:/clientes.csv creado correctamente");
        } catch (IOException e) {
            System.out.println("No se ha podido crear el archivo D:/clientes.txt");
        }
    }

    private void leeClientes(){

        //SIMPLEMENTE LEER LAS LÍNEAS DEL ARCHIVO clientes.txt Y MOSTRARLAS POR PANTALLA
        System.out.println("\nListado de Clientes directamente desde clientes.txt\n");
        try(Scanner scClientes=new Scanner(new File("D:/clientes.txt"))){
            while (scClientes.hasNextLine()){
                System.out.println(scClientes.nextLine());
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }

        //CREAR UNA NUEVA COLECCIÓN DE TIPO HASHMAP A PARTIR DEL ARCHIVO clientes.csv
        HashMap <String,Cliente> clientesAux = new HashMap();
        try(Scanner scClientes=new Scanner(new File("D:/clientes.csv"))){
            while (scClientes.hasNextLine()){
                String [] atributos = scClientes.nextLine().split("[,]");
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]);
                clientesAux.put(atributos[0], c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        System.out.println("\nListado de Clientes del nuevo HashMap clientesAux\n");
        clientesAux.values().forEach(System.out::println);
    }

    private void guardaArticulosPorSeccion(){
        //GUARDAMOS LOS
        try (BufferedWriter bwPerifericos = new BufferedWriter(new FileWriter("perifericos.csv"));
             BufferedWriter bwAlmacenamiento = new BufferedWriter(new FileWriter("almacenamiento.csv"));
             BufferedWriter bwImpresoras = new BufferedWriter(new FileWriter("impresoras.csv"));
             BufferedWriter bwMonitores = new BufferedWriter(new FileWriter("monitores.csv")))
        {
            for (Articulo a : articulos.values()) {
                switch (a.getIdArticulo().charAt(0)) {
                    case '1':
                        bwPerifericos.write(a.getIdArticulo()+","+a.getDescripcion()+","+a.getExistencias()+","+a.getPvp()+"\n");
                        break;
                    case '2':
                        bwAlmacenamiento.write(a.getIdArticulo()+","+a.getDescripcion()+","+a.getExistencias()+","+a.getPvp()+"\n");
                        break;
                    case '3':
                        bwImpresoras.write(a.getIdArticulo()+","+a.getDescripcion()+","+a.getExistencias()+","+a.getPvp()+"\n");
                        break;
                    case '4':
                        bwMonitores.write(a.getIdArticulo()+","+a.getDescripcion()+","+a.getExistencias()+","+a.getPvp()+"\n");
                        break;
                }
            }
            System.out.println("Archivos creados correctamente");
        } catch (IOException e) {
            System.out.println("No se han podido crear los archivos");
            File f=new File("perifericos.csv");
            f.delete();
            f=new File("almacenamiento.csv");
            f.delete();
            f=new File("impresoras.csv");
            f.delete();
            f=new File("monitores.csv");
            f.delete();
        }
    }

    private void leeArticulosPorSeccion(){
        HashMap <String,Articulo> articulosAux = new HashMap();
        String lineaArchivo; //para ir leyendo cada línea en los archivos
        String [] atributos; //Para "romper" cada línea en los atributos separados por ,

        try (Scanner scPerifericos = new Scanner(new File("perifericos.csv"));
             Scanner scAlmacenamiento = new Scanner(new File("almacenamiento.csv"));
             Scanner scImpresoras = new Scanner(new File("impresoras.csv"));
             Scanner scMonitores = new Scanner(new File("monitores.csv")))
        {
            /* ADEMÁS DE MOSTRAR POR PANTALLA EL CONTENIDO DE LOS 4 ARCHIVOS CREADOS
            APROVECHAMOS PARA RECONSTRUIR UN HashMap CON LOS ARTICULOS DE LOS 4 ARCHIVOS */

            System.out.println("\nPERIFERICOS:" );
            while (scPerifericos.hasNextLine()){
                //leo una línea del archivo
                lineaArchivo = scPerifericos.nextLine();
                //Imprimo línea por pantalla
                System.out.println(lineaArchivo);
                //con esa línea creamos un nuevo Articulo para añadir al HashMap articulosAux
                atributos = lineaArchivo.split("[,]");
                articulosAux.put(atributos[0],
                        new Articulo(atributos[0],atributos[1],Integer.parseInt(atributos[2]),Double.parseDouble(atributos[3])));

            }
            System.out.println("\nALMACENAMIENTO:" );
            while (scAlmacenamiento.hasNextLine()){
                lineaArchivo = scAlmacenamiento.nextLine();
                System.out.println(lineaArchivo);
                atributos = lineaArchivo.split("[,]");
                articulosAux.put(atributos[0],
                        new Articulo(atributos[0],atributos[1],Integer.parseInt(atributos[2]),Double.parseDouble(atributos[3])));
            }
            System.out.println("\nIMPRESORAS:" );
            while (scImpresoras.hasNextLine()){
                lineaArchivo = scImpresoras.nextLine();
                System.out.println(lineaArchivo);
                atributos = lineaArchivo.split("[,]");
                articulosAux.put(atributos[0],
                        new Articulo(atributos[0],atributos[1],Integer.parseInt(atributos[2]),Double.parseDouble(atributos[3])));
            }
            System.out.println("\nMONITORES:" );
            while (scMonitores.hasNextLine()){
                lineaArchivo = scMonitores.nextLine();
                System.out.println(lineaArchivo);
                atributos = lineaArchivo.split("[,]");
                articulosAux.put(atributos[0],
                        new Articulo(atributos[0],atributos[1],Integer.parseInt(atributos[2]),Double.parseDouble(atributos[3])));

            }
        }catch(IOException e){
            System.out.println(e.toString());
        }

        //MOSTRAMOS POR PANTALLA EL CONTENIDO DEL HASHMAP articulosAux QUE HEMOS IDO CREANDO CON LOS ARTICULOS DE LOS 4 ARCHIVOS
        System.out.println("");
        for (Articulo a:articulosAux.values()){
            System.out.println(a);
        }
    }

    public void ExportarColecciones() {
        try (ObjectOutputStream oosArticulos = new ObjectOutputStream(new FileOutputStream("D:/articulos.dat"));
             ObjectOutputStream oosClientes = new ObjectOutputStream(new FileOutputStream("D:/clientes.dat"));
             ObjectOutputStream oosPedidos = new ObjectOutputStream (new FileOutputStream("D:/pedidos.dat"))) {

            for (Articulo a : articulos.values()) {
                oosArticulos.writeObject(a);
            }
            for (Cliente c:clientes.values()) {
                oosClientes.writeObject(c);
            }
            for (Pedido p:pedidos){
                oosPedidos.writeObject(p);
            }
            System.out.println("Copia de seguridad realizada con éxito.");

        } catch (IOException ex) {
            System.out.println("No se han podido crear los archivos correctamente, "
                    + "revise unidades de almacenamiento e inténtelo de nuevo");
            File f=new File("D:/articulos.dat");
            f.delete();
            f=new File("D:/clientes.dat");
            f.delete();
            f=new File("D:/pedidos.dat");
            f.delete();
        }
    }

    public void importarColecciones() {
        /* HAY QUE LEER DESDE CADA ARCHIVO POR SEPARADO PORQUE SI INTENTAMOS METERLO TODO EN EL MISMO
        TRY-CATCH AL LLEGAR AL FINAL DEL PRIMER ARCHIVO SE PRODUCE LA EOFException Y SÓLO SE
        LEERÍA BIEN EL PRIMER ARCHIVO, EL RESTO NO */

        try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream("D:/articulos.dat"))){
            Articulo a;
            while ( (a=(Articulo)oisArticulos.readObject()) != null){
                articulos.put(a.getIdArticulo(), a);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (EOFException e){
            System.out.println("Finalizada la lectura del archivo articulos.dat");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream oisClientes = new ObjectInputStream(new FileInputStream("D:/clientes.dat"))){
            Cliente c;
            while ( (c=(Cliente)oisClientes.readObject()) != null){
                clientes.put(c.getIdCliente(), c);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (EOFException e){
            System.out.println("Finalizada la lectura del archivo clientes.dat");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream oisPedidos = new ObjectInputStream(new FileInputStream("D:/pedidos.dat"))){
            Pedido p;
            while ( (p=(Pedido)oisPedidos.readObject()) != null){
                pedidos.add(p);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (EOFException e){
            System.out.println("Finalizada la lectura del archivo pedidos.dat");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.toString());
        }
    }

    // Getters para el test junit5
    public HashMap<String, Articulo> getArticulos() {
        return articulos;
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }
}
