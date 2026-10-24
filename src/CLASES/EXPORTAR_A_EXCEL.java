package CLASES;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class EXPORTAR_A_EXCEL {

    public void exportarExcel(JTable tabla) throws IOException {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Excel (*.xls)", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            File archivoXLS = new File(ruta);

            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }

            archivoXLS.createNewFile();

            Workbook libro = new HSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");

            // ------------------------------
            // 1. ENCABEZADOS
            // ------------------------------
            Row filaEncabezado = hoja.createRow(0);
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                Cell celda = filaEncabezado.createCell(c);
                celda.setCellValue(tabla.getColumnName(c));
            }

            // ------------------------------
            // 2. LLENAR LOS DATOS
            // ------------------------------
            int filaExcel = 1;

            for (int f = 0; f < tabla.getRowCount(); f++) {
                Row fila = hoja.createRow(filaExcel);
                filaExcel++;

                for (int c = 0; c < tabla.getColumnCount(); c++) {

                    Cell celda = fila.createCell(c);
                    Object valor = tabla.getValueAt(f, c);

                    if (valor == null) {
                        celda.setCellValue("");
                        continue;
                    }

                    // Asignar correctamente el tipo
                    if (valor instanceof Number) {
                        celda.setCellValue(Double.parseDouble(valor.toString()));
                    } else {
                        celda.setCellValue(valor.toString());
                    }
                }
            }

            // ------------------------------
            // 3. TOTAL AUTOMÁTICO (en la última fila)
            // ------------------------------

            int filaTotal = tabla.getRowCount() + 1;
            Row fila = hoja.createRow(filaTotal);

            Cell textoTotal = fila.createCell(0);
            textoTotal.setCellValue("TOTAL VENTAS");

            // SUPONEMOS QUE EL MONTO_VENTA ESTA EN LA COLUMNA 3 (ÍNDICE 3)
            int columnaMonto = 3;

            Cell celdaTotal = fila.createCell(columnaMonto);

            String formula = "SUM(" 
                    + getExcelColumnName(columnaMonto + 1) 
                    + "2:" 
                    + getExcelColumnName(columnaMonto + 1) 
                    + (tabla.getRowCount() + 1) 
                    + ")";

            celdaTotal.setCellFormula(formula);

            // ------------------------------
            // 4. AJUSTAR TAMAÑO DE COLUMNAS
            // ------------------------------
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                hoja.autoSizeColumn(c);
            }

            // ------------------------------
            // 5. GUARDAR DOCUMENTO
            // ------------------------------
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            libro.write(archivo);
            archivo.close();

            // ABRIR AUTOMÁTICAMENTE
            Desktop.getDesktop().open(archivoXLS);
        }
    }

    // -------------------------------------------
    // FUNCIÓN PARA OBTENER LETRA DE COLUMNA EXCEL
    // -------------------------------------------
    private String getExcelColumnName(int number) {
        StringBuilder columnName = new StringBuilder();
        while (number > 0) {
            int remainder = (number - 1) % 26;
            columnName.insert(0, (char) (remainder + 'A'));
            number = (number - 1) / 26;
        }
        return columnName.toString();
    }
}
