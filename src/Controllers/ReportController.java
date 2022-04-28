/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

//import Controllers.DataContext.DataContext;
import Entities.Articulo;
import Entities.ArticuloComputed;
import java.text.DecimalFormat;
//import java.sql.Connection;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author euris
 */
public class ReportController {
    public void renderListadoExistencia(List<Articulo> articulos) {
        try {            
            List<ArticuloComputed> articulosComputed = new ArrayList<>();
            DecimalFormat customFormat = new DecimalFormat("$ ,###.00");
            float generalSubTotal = 0;
            float generalItbis = 0;
            float generalTotal = 0;
            
            for (Articulo articulo: articulos) {
                ArticuloComputed articuloComputed = new ArticuloComputed();
                float subTotal = articulo.getSalePrice() * (1 - (articulo.getItbis()/100));
                float itbis = articulo.getSalePrice() * (articulo.getItbis()/100);
                float total = subTotal + itbis;
                
                float finalSubTotal = articulo.getQuantity() * subTotal;
                float finalItbis = articulo.getQuantity() * itbis;
                float finalTotal = articulo.getQuantity() * total;
                                
                articuloComputed.setName(articulo.getName());
                articuloComputed.setQuantity(String.valueOf(articulo.getQuantity()));
                articuloComputed.setSubTotal(customFormat.format(finalSubTotal));
                articuloComputed.setItbis(customFormat.format(finalItbis));
                articuloComputed.setTotal(customFormat.format(finalTotal));
                articulosComputed.add(articuloComputed);
                
                generalSubTotal = generalSubTotal + finalSubTotal;
                generalItbis = generalItbis + finalItbis;
                generalTotal = generalTotal + finalTotal;
            }
            
            HashMap parameters = new HashMap();
            parameters.put("generalSubTotal", customFormat.format(generalSubTotal));
            parameters.put("generalItbis", customFormat.format(generalItbis));
            parameters.put("generalTotal", customFormat.format(generalTotal));
            
            String reportPath = "C:\\Users\\euris\\Escritorio\\NetBeans Projects\\SistemaFacturacion\\src\\Views\\Reports\\ListadoExistencia.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(articulosComputed));
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
