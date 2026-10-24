package CLASES;

import java.awt.*;
import java.awt.print.*;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.print.PrintService;

public class IMPRIMIR_TICKET_POS58 implements Printable {

    private String factura;
    private String fecha;
    private String cliente;
    private String usuario;
    private String total;
    private JTable tabla;

    public IMPRIMIR_TICKET_POS58(String factura, String fecha, String cliente,
                                 String usuario, String total, JTable tabla) {
        this.factura = factura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.usuario = usuario;
        this.total = total;
        this.tabla = tabla;
    }

    // ============================================
    //  CONFIGURAR FORMATO DE PÁGINA POS58
    // ============================================
    private PageFormat configurarPagina() {

        Paper paper = new Paper();

        double width = 164;   // ancho POS58 en puntos (58 mm)
        double height = 600;  // alto de ticket (variable)

        paper.setSize(width, height);
        paper.setImageableArea(0, 0, width, height);

        PageFormat pf = new PageFormat();
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.PORTRAIT);

        return pf;
    }

    // ============================================
    //  DIBUJAR EL CONTENIDO DEL TICKET
    // ============================================
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {

        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = 10;

        // CABECERA
        g.drawString(" FARMAVITAL ", 40, y); y += 15;
        g.drawString("------------------------------", 0, y); y += 15;

        // DATOS DEL TICKET
        g.drawString("Boleta: " + factura, 0, y); y += 15;
        g.drawString("Fecha : " + fecha, 0, y); y += 15;
        g.drawString("Cliente: " + cliente, 0, y); y += 15;
        g.drawString("Atendido por: " + usuario, 0, y); y += 15;

        g.drawString("------------------------------", 0, y); y += 15;
        g.drawString("Cant  Producto           Importe", 0, y); y += 15;
        g.drawString("------------------------------", 0, y); y += 15;

        // LISTA DE PRODUCTOS
        TableModel model = tabla.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {

            String cantidad = model.getValueAt(i, 0).toString();
            String descripcion = model.getValueAt(i, 2).toString();
            String subtotal = model.getValueAt(i, 4).toString();

            // recortar descripción para no romper el ancho
            if (descripcion.length() > 16) {
                descripcion = descripcion.substring(0, 16);
            }

            g.drawString(cantidad + "   " + descripcion, 0, y);
            g.drawString(subtotal, 160, y);  
            y += 15;
        }

        g.drawString("------------------------------", 0, y); y += 15;
        g.drawString("TOTAL: S/. " + total, 0, y); y += 15;
        g.drawString("------------------------------", 0, y); y += 15;

        g.drawString("Gracias por su compra", 30, y);

        return PAGE_EXISTS;
    }

    // ============================================
    //  IMPRIMIR DIRECTAMENTE EN IMPRESORA POS58
    // ============================================
    public void imprimirDirecto() {

        PrinterJob pj = PrinterJob.getPrinterJob();

        // Asignar formato POS58
        pj.setPrintable(this, configurarPagina());

        // Buscar impresora térmica automáticamente
        for (PrintService impresora : PrinterJob.lookupPrintServices()) {
            String nombre = impresora.getName().toLowerCase();

            if (nombre.contains("pos") || nombre.contains("58") || nombre.contains("thermal")) {
                try {
                    pj.setPrintService(impresora);
                    System.out.println("Imprimiendo en: " + impresora.getName());
                    break;
                } catch (PrinterException e) {
                    e.printStackTrace();
                }
            }
        }

        // Enviar a impresión
        try {
            pj.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
