/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestionventaentradasteatromoro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author josez
 */
public class GestionVentaEntradasTeatroMoro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GestionVentaEntradasTeatroMoro app = new GestionVentaEntradasTeatroMoro();
        app.Bienvenida();
    }
    //Propiedades
    private List<Entrada> entradasVendidas = new ArrayList<>();
    
    private TipoEntrada entradaSeleccionada;
    private Tarifa tarifaSeleccionada;
    private double valorEntradaSeleccionada;
    //JFrames que se iran manipulando con las acciones de los botones.
    private JFrame frameMenuPrincipal;
    private JFrame frameVentaEntradas;
    //Elementos visuales que necesitan volver a su estado incial luego de cada compra
    private JButton botonCompraEntrada;
    private JComboBox<String> comboTipoEntrada;
    private JComboBox<String> comboTipoTarifa;
    private JLabel labelValorEntradaSeleccionada;
    private int edad = 0;

    //Getters and Setters
    public TipoEntrada getEntradaSeleccionada() {
        return entradaSeleccionada;
    }

    public void setEntradaSeleccionada(TipoEntrada entradaSeleccionada) {
        this.entradaSeleccionada = entradaSeleccionada;
    }

    public Tarifa getTarifaSeleccionada() {
        return tarifaSeleccionada;
    }

    public void setTarifaSeleccionada(Tarifa tarifaSeleccionada) {
        this.tarifaSeleccionada = tarifaSeleccionada;
    }
    public double getValorEntradaSeleccionada() {
        return valorEntradaSeleccionada;
    }

    public void setValorEntradaSeleccionada(double valorEntradaSeleccionada) {
        this.valorEntradaSeleccionada = valorEntradaSeleccionada;
    }
    
    //Metodos de la clase
    public void VenderEntrada(TipoEntrada tipo, Tarifa tarifa){
        
        double valor = this.AsignarValorEntrada(tipo, tarifa);
        Entrada entrada = new Entrada(tipo, tarifa, valor);
        this.entradasVendidas.add(entrada);
        System.out.println("Gracias por su compra, disfrute la función");
    }
    
    public double AsignarValorEntrada(TipoEntrada tipo, Tarifa tarifa){
        double valor = 0.0;
        
        switch (tipo){
            case VIP:
                valor = 25000;
                if (tarifa == Tarifa.ESTUDIANTE){
                    valor = valor*0.9;
                }
                if(tarifa == Tarifa.PREFERENCIAL){
                    valor = valor*0.85;
                }
                break;
            case PLATEA_BAJA: 
                valor = 19000;
                if (tarifa == Tarifa.ESTUDIANTE){
                    valor = valor*0.9;
                }
                if(tarifa == Tarifa.PREFERENCIAL){
                    valor = valor*0.85;
                }
                break;
            case PLATEA_ALTA:
                valor = 11000;
                if (tarifa == Tarifa.ESTUDIANTE){
                    valor = valor*0.9;
                }
                if(tarifa == Tarifa.PREFERENCIAL){
                    valor = valor*0.85;
                }
                break;
            case PALCOS: 
                valor = 7200;
                if (tarifa == Tarifa.ESTUDIANTE){
                    valor = valor*0.9;
                }
                if(tarifa == Tarifa.PREFERENCIAL){
                    valor = valor*0.85;
                }
                break;
        }
        
        return valor;
    }
    
    public void ActivarModoGrafico(){
        this.frameMenuPrincipal = this.EstablecerFrame();
        this.frameMenuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frameMenuPrincipal.setSize(400, 300);
        this.frameMenuPrincipal.setLayout(new FlowLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel labelPrograma = new JLabel("SISTEMA DE GESTION DE VENTAS TEATRO MORO");
         // Obtener la fuente actual del label
        Font currentFont = labelPrograma.getFont();
        // Crear una nueva fuente basada en la actual pero estableciendo el estilo en negrita
        Font boldFont = new Font(currentFont.getName(), Font.BOLD, currentFont.getSize());
        // Establecer la nueva fuente en el label
        labelPrograma.setFont(boldFont);
        
        JLabel labelMenu = new JLabel("MENU PRINCIPAL");
        JButton botonComprarEntrada = new JButton("Comprar Entrada");
        botonComprarEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionVentaEntradasTeatroMoro.this.frameMenuPrincipal.setVisible(false); // Ocultar el frame actual
                GestionVentaEntradasTeatroMoro.this.VenderEntradas();
            }
        });
        
        // Crear el botón para cerrar la aplicación
        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cerrar la aplicación
            }
        });
        
        //Añadir labels
        panel.add(labelPrograma, BorderLayout.CENTER);
        panel.add(labelMenu);
        // Añadir el botón al panel
        panel.add(botonComprarEntrada);
        // Añadir el botón al panel
        panel.add(botonCerrar);
        
        // Añadir el panel con el botón al JFrame
        this.frameMenuPrincipal.add(panel);
        // Mostrar la ventana
        this.frameMenuPrincipal.setVisible(true);
    }
    
    private void VenderEntradas(){
        Map<TipoEntrada, String> mapTipoEntrada = this.MapeoComboTipoEntrada();
        Map<Tarifa, String> mapTipoTarifa = this.MapeoComboTipoTarifa();
        this.frameVentaEntradas = this.EstablecerFrame();
        this.frameVentaEntradas.setLayout(new FlowLayout());
        
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Campo para lectura de la edad
        JLabel labelCampoEdad = new JLabel("Ingrese su edad:");
        JTextField campoEdad = EstablecerCampoLecturaEdad();
        //Combo tipo de entrada
        JLabel labelComboTipoEntrada = new JLabel("Tipo de entrada:");
        this.comboTipoEntrada = this.EstablecerComboTipoEntrada(mapTipoEntrada);
        //Combo tarifa
        JLabel labelComboTipoTarifa =  new JLabel("Tipo de tarifa:");
        this.comboTipoTarifa = this.EstablecerComboTipoTarifa(mapTipoTarifa);
        this.comboTipoTarifa.setEnabled(false);
        
        this.labelValorEntradaSeleccionada =  new JLabel("Valor entrada: "+ this.getValorEntradaSeleccionada());
        JButton buttonSeleccionEntrada = this.EstablecerBotonSeleccionarEntrada(this.labelValorEntradaSeleccionada);
        
        JButton botonComprarEntrada = this.EstablecerBotonComprarEntrada(this.frameVentaEntradas);
        botonComprarEntrada.setEnabled(false);
        this.botonCompraEntrada = botonComprarEntrada;
        
        panel.add(labelCampoEdad);
        panel.add(campoEdad);
        panel.add(labelComboTipoEntrada);
        panel.add(this.comboTipoEntrada);
        panel.add(labelComboTipoTarifa);
        panel.add(this.comboTipoTarifa);
        panel.add(buttonSeleccionEntrada);
        panel2.add(this.labelValorEntradaSeleccionada);
        panel2.add(botonComprarEntrada);
        panel.add(panel2);
        
        this.frameVentaEntradas.getContentPane().add(panel);
        this.frameVentaEntradas.setVisible(true);
    }
    
    private JFrame EstablecerFrame(){
        JFrame frame = new JFrame("Sistema de gestion de venta de entradas Teatro Moro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setSize(800, 600);
        return frame;
    }
    
    private JTextField EstablecerCampoLecturaEdad(){
        JTextField campoNumerico = new JTextField(10);
        PlainDocument doc = (PlainDocument) campoNumerico.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String antesOffset = textoActual.substring(0, offset);
                String DespuesOffset = textoActual.substring(offset + length);
                String resultadoPropuesto = antesOffset + text + DespuesOffset;

                if (resultadoPropuesto.isEmpty()) return; // permite borrado

                try {
                    int nuevoValor = Integer.parseInt(resultadoPropuesto);
                    if (nuevoValor >= 1 && nuevoValor <= 120) {
                        super.replace(fb, offset, length, text, attrs);
                        GestionVentaEntradasTeatroMoro.this.edad = nuevoValor;
                        if(nuevoValor > 0 && nuevoValor <= 18)
                            GestionVentaEntradasTeatroMoro.this.setTarifaSeleccionada(Tarifa.ESTUDIANTE);
                        if(nuevoValor > 18 && nuevoValor < 65)
                            GestionVentaEntradasTeatroMoro.this.setTarifaSeleccionada(Tarifa.GENERAL);
                        if(nuevoValor >= 65 && nuevoValor <= 120)
                            GestionVentaEntradasTeatroMoro.this.setTarifaSeleccionada(Tarifa.PREFERENCIAL);
                    } else {
                        Toolkit.getDefaultToolkit().beep(); // alerta de error
                    }
                } catch (NumberFormatException e) {
                    Toolkit.getDefaultToolkit().beep(); // alerta de error
                }
            }
        });
        
        return campoNumerico;
    }
    
    private JComboBox<String> EstablecerComboTipoEntrada(Map<TipoEntrada, String> mapTipoEntrada){
        JComboBox<String> comboBox = new JComboBox<>();
        
        for (TipoEntrada tipo : TipoEntrada.values()) {
            comboBox.addItem(mapTipoEntrada.get(tipo));
        }
        //Se establece el valor por defecto en caso de que no se interactue con el combobox.
        comboBox.setSelectedItem(mapTipoEntrada.get(TipoEntrada.VIP));
        
        /*Se establece propiedad de clase con valor por defecto
        **consecuentemte con lo que se ve en pantalla.
        */
        this.setEntradaSeleccionada(TipoEntrada.VIP);
        
        comboBox.addActionListener(e -> {
            String seleccion = (String) comboBox.getSelectedItem();
            TipoEntrada tipoSeleccionado = null;
            // Buscar el tipo de entrada basado en el texto personalizado
            for (Map.Entry<TipoEntrada, String> entrada : mapTipoEntrada.entrySet()) {
                if (entrada.getValue().equals(seleccion)) {
                    tipoSeleccionado = entrada.getKey();
                    break;
                }
            }
           
            if(tipoSeleccionado!= null)
                this.setEntradaSeleccionada(tipoSeleccionado);
                
            System.out.println("Seleccionado: " + tipoSeleccionado);
        });
        
        return comboBox;
    }
    
    private JComboBox<String> EstablecerComboTipoTarifa (Map<Tarifa, String> mapTipoTarifa ){
        JComboBox<String> comboBox = new JComboBox<>();
        
        for (Tarifa tipo : Tarifa.values()) {
            comboBox.addItem(mapTipoTarifa.get(tipo));
        }
        
        //Se establece el valor por defecto en caso de que no se interactue con el combobox.
        comboBox.setSelectedItem(mapTipoTarifa.get(Tarifa.GENERAL));
        /*Se establece propiedad de clase con valor por defecto
        **consecuentemte con lo que se ve en pantalla.
        */
        this.setTarifaSeleccionada(Tarifa.GENERAL);
        
        comboBox.addActionListener(e -> {
            String seleccion = (String) comboBox.getSelectedItem();
            Tarifa tipoSeleccionado = null;
            // Buscar el tipo de entrada basado en el texto personalizado
            for (Map.Entry<Tarifa, String> entrada : mapTipoTarifa.entrySet()) {
                if (entrada.getValue().equals(seleccion)) {
                    tipoSeleccionado = entrada.getKey();
                    break;
                }
            }
            if(tipoSeleccionado!= null)
                this.setTarifaSeleccionada(tipoSeleccionado);
            System.out.println("Seleccionado: " + tipoSeleccionado);
        });
        
        return comboBox;
    }
    
    private JButton EstablecerBotonSeleccionarEntrada(JLabel labelValorEntrada){
        JButton button = new JButton("Seleccionar Entrada");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double valorEntrada = GestionVentaEntradasTeatroMoro.this.AsignarValorEntrada(GestionVentaEntradasTeatroMoro.this.getEntradaSeleccionada()
                        ,GestionVentaEntradasTeatroMoro.this.getTarifaSeleccionada());
                GestionVentaEntradasTeatroMoro.this.setValorEntradaSeleccionada(valorEntrada);   
                labelValorEntrada.setText("1 Entrada "+GestionVentaEntradasTeatroMoro.this.getEntradaSeleccionada()+" $"+ valorEntrada); 
                
                //Se activa boton de compra una vez que el usuario seleccione una entrada
                GestionVentaEntradasTeatroMoro.this.botonCompraEntrada.setEnabled(true);
                System.out.println("¡Botón pulsado! "+ valorEntrada);
            }
        });
        
        return button;
    }
    
    private JButton EstablecerBotonComprarEntrada(JFrame frame){
        JButton button = new JButton("Comprar Entrada");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionVentaEntradasTeatroMoro.this.VenderEntrada(
                        GestionVentaEntradasTeatroMoro.this.getEntradaSeleccionada(),
                        GestionVentaEntradasTeatroMoro.this.getTarifaSeleccionada());
                GestionVentaEntradasTeatroMoro.this.EstablecerPopupCompra(frame);
            }
        });
        
        return button;
    }
    
    private void EstablecerPopupCompra(JFrame frame){
        JDialog dialog = new JDialog(frame, "Gracias por su compra", true); // El tercer argumento 'true' hace que sea modal
        dialog.setLayout(new FlowLayout());
        dialog.setSize(350, 150);
        JLabel label = new JLabel("Gracias por su compra, disfrute la función");
       
        JButton button = new JButton("Aceptar");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restablcer valores antes de continuar
                GestionVentaEntradasTeatroMoro.this.comboTipoEntrada.setSelectedIndex(0);
                GestionVentaEntradasTeatroMoro.this.comboTipoTarifa.setSelectedIndex(0);
                GestionVentaEntradasTeatroMoro.this.setValorEntradaSeleccionada(0);
                GestionVentaEntradasTeatroMoro.this.labelValorEntradaSeleccionada
                        .setText("Valor entrada: "+ GestionVentaEntradasTeatroMoro.this.getValorEntradaSeleccionada());
                
                dialog.dispose();
            }
        });
        
        dialog.getContentPane().add(label);
        dialog.add(button);
        dialog.setLocationRelativeTo(frame); // Centrar respecto al frame principal
        dialog.setVisible(true);
    }
    
    private Map<TipoEntrada, String> MapeoComboTipoEntrada(){
        Map<TipoEntrada, String> descripcionEntradas = new HashMap<>();
        descripcionEntradas.put(TipoEntrada.VIP, "Entrada VIP");
        descripcionEntradas.put(TipoEntrada.PLATEA_ALTA, "Platea Alta");
        descripcionEntradas.put(TipoEntrada.PLATEA_BAJA, "Platea Baja");
        descripcionEntradas.put(TipoEntrada.PALCOS, "Palcos");
        
        return descripcionEntradas;
    }
    
    private Map<Tarifa, String> MapeoComboTipoTarifa(){
        Map<Tarifa, String> descripcionTarifas = new HashMap<>();
        descripcionTarifas.put(Tarifa.GENERAL, "Tarifa General");
        descripcionTarifas.put(Tarifa.ESTUDIANTE, "Tarifa Estudiante");
        descripcionTarifas.put(Tarifa.PREFERENCIAL, "Tarifa Preferencial");
       
        return descripcionTarifas;
    }
    
    public void Bienvenida(){
        System.out.println("Bienvenido al sistema de gestion de venta de entradas del Teatro Moro");
        System.out.println("Desea activar el modo grafico?");
        System.out.println("Si/No");
        
        Scanner scan = new Scanner(System.in);
        String respuestaUsuario = scan.nextLine();
        String patronRespuestaValida = "^(?i)(si|s|yes|y|no|n)$";
        String patronRespuestaPositiva = "^(?i)(si|s|yes|y)$";
        String patronRespuestaNegativa = "^(?i)(no|n)$";
        
        if(!respuestaUsuario.matches(patronRespuestaValida)){
            System.out.println("Respuesta no valida. Por favor intente nuevamente.");
            this.Bienvenida();
        }
        
        if(!respuestaUsuario.matches(patronRespuestaPositiva)){
            //Se continua con ejecucion por consola.
            this.AppConsola();
            return;
        }
        
        this.ActivarModoGrafico();
    }
    
    private void AppConsola(){
        boolean condicion = true;
        
        while(condicion){
            int tipoEntrada = 0;
            int tipoTarifa = 0;
            int edad = 0;
            TipoEntrada[] valores = TipoEntrada.values();
            Tarifa[] valoresT = Tarifa.values();
            
            
            System.out.println("Esta en el mododo consola del sistema de gestion de venta de entradas del Teatro Moro");
            System.out.println("Seleccione el tipo de entrada que desea comprar: ");
            System.out.println("1.-Vip");
            System.out.println("2.-Platea baja");
            System.out.println("3.-Platea alta");
            System.out.println("4.-Palcos");
            tipoEntrada = this.LeerNumero();
            String tipoEntradaStr = Integer.toString(tipoEntrada);
            String regexTEntrada = "^[1-4]$";
            
            while(!tipoEntradaStr.matches(regexTEntrada)){
                System.out.println("La opcion seleccionada no es valida");
                System.out.println("Seleccione una de las siguientes: ");
                System.out.println("1.-Vip");
                System.out.println("2.-Platea baja");
                System.out.println("3.-Platea alta");
                System.out.println("4.-Palcos");
                tipoEntrada = this.LeerNumero();
                tipoEntradaStr = Integer.toString(tipoEntrada);
            }
            tipoTarifa = 0;
            System.out.println("Ingrese su edad: ");
            this.edad = this.LeerNumero();
            
            if(this.edad >0 && this.edad<=18){
                System.out.println("Su tarifa es: ");
                System.out.println("2.-Estudiante");
                tipoTarifa =2;
            }
            
            if(this.edad >0 && this.edad >18 && edad <65){
                System.out.println("Su tarifa es: ");
                System.out.println("1.-Publico General");
                tipoTarifa =1;
            }
            
             if(this.edad >=65 && this.edad <=120){
                System.out.println("Su tarifa es: ");
                System.out.println("3.-Preferencial");
                tipoTarifa =3;
            }
             
            //Se le quita 1 para que calze el indice del arreglo con lo seleccionado por el usuario
            this.VenderEntrada(valores[tipoEntrada-1], valoresT[tipoTarifa-1]);
            System.out.println("El valor de su entrada es: "+ this.AsignarValorEntrada(valores[tipoEntrada-1],valoresT[tipoTarifa-1]));
            System.out.println("Desea continuar? Si/No");
            
            Scanner scan = new Scanner(System.in);
            String respuestaUsuario = scan.nextLine();
            String patronRespuestaValida = "^(?i)(si|s|yes|y|no|n)$";
            String patronRespuestaPositiva = "^(?i)(si|s|yes|y)$";
            String patronRespuestaNegativa = "^(?i)(no|n)$";
            
            while(!respuestaUsuario.matches(patronRespuestaValida)){
                System.out.println("Respuesta no valida. Por favor intente nuevamente.");
                respuestaUsuario = scan.nextLine();
            }

            if(!respuestaUsuario.matches(patronRespuestaPositiva)){
                condicion = false;
            }
        } 
    }
    
    private int LeerNumero(){
        try{
            Scanner scan = new Scanner(System.in);
            int respuestaUsuario = scan.nextInt();
            return respuestaUsuario;
        }catch(Exception e){
            System.out.print("Respuesta no valida");
            return this.LeerNumero();
        }
    }
}

enum Tarifa{
   GENERAL,
   ESTUDIANTE,
   PREFERENCIAL
}

enum TipoEntrada{
    VIP,
    PLATEA_ALTA,
    PLATEA_BAJA,
    PALCOS
}

class Entrada {
    
    private TipoEntrada tipoEntrada;
    private Tarifa tarifa;
    private double valor;
    
    //Contructor de la clase entrada
    public Entrada(TipoEntrada tipoEntrada, Tarifa tarifa, double valor) {
        this.tipoEntrada = tipoEntrada;
        this.tarifa = tarifa;
        this.valor = valor;
    }
    

    public TipoEntrada getTipoEntrada() {
        return this.tipoEntrada;
    }

    public void setTipoEntrada(TipoEntrada TipoEntrada) {
        this.tipoEntrada = TipoEntrada;
    }

    public Tarifa getTarifa() {
        return this.tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return "Entrada:{" + "TipoEntrada:" + tipoEntrada + ", Tarifa:" + tarifa + ", Valor:" + valor + '}';
    }
}