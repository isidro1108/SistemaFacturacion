/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Entities.Articulo;
import Entities.ArticuloComputed;
import Entities.ArticuloFactura;
import Entities.Cliente;
import Entities.Empleado;
import Entities.Factura;
import Entities.SoldItem;
import java.text.DecimalFormat;
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
    private final EmpleadoController empleadoController;
    private final ClienteController clienteController;
    private final DecimalFormat customFormatPrices;
    
    public ReportController() {
        this.empleadoController = new EmpleadoController();
        this.clienteController = new ClienteController();
        this.customFormatPrices = new DecimalFormat("$ ,##0.00");
    }
    
    public void renderListadoExistencia(List<Articulo> articulos) {
        try {            
            List<ArticuloComputed> articulosComputed = new ArrayList<>();
            DecimalFormat customFormat = new DecimalFormat("$ ,##0.00");
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
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void renderNuevaFactura(Factura factura) {
        try {
            List<SoldItem> soldItems = new ArrayList<>();
            HashMap parameters = new HashMap();
            Empleado empleado = this.empleadoController.getById(factura.getIdEmployee());
            Cliente cliente = this.clienteController.getById(factura.getIdCustomer());
            String employeeFullName = empleado.getName() + " " + empleado.getLastName();
            String customerFullname = cliente.getName() + " " + cliente.getLastName();
            
            float subTotal = 0;
            float itbis = 0;
            
            for (ArticuloFactura soldItem: factura.getSoldItems()) {
                SoldItem item = new SoldItem();
                
                item.setCode(soldItem.getCode());
                item.setName(soldItem.getName());
                item.setQuantity(String.valueOf(soldItem.getQuantity()));
                item.setSale(this.customFormatPrices.format(soldItem.getSale()));
                item.setItbis(this.customFormatPrices.format(soldItem.getItbis()));
                item.setSubTotal(this.customFormatPrices.format(soldItem.getSubTotal()));
                soldItems.add(item);
                
                subTotal+= soldItem.getSubTotal();
                itbis+= soldItem.getItbis();
            }
            
            float total = subTotal + itbis;
            String totalFormated = this.customFormatPrices.format(total);
            
            parameters.put("code", factura.getCode());
            parameters.put("RNC", factura.getRnc());
            parameters.put("NIF", factura.getNif());
            parameters.put("generalSubTotal", this.customFormatPrices.format(subTotal));
            parameters.put("generalItbis", this.customFormatPrices.format(itbis));
            parameters.put("total", totalFormated);
            parameters.put("payCard", "Tarjeta".equals(factura.getPaymentType()) ? totalFormated : "$ 0.00");
            parameters.put("cash", "Al Contado".equals(factura.getInvoiceType()) ? this.customFormatPrices.format(factura.getCash()) : "$ 0.00");
            parameters.put("payBack", "Al Contado".equals(factura.getInvoiceType()) && "Efectivo".equals(factura.getPaymentType()) ? this.customFormatPrices.format(factura.getCash() - total) : "$ 0.00");
            parameters.put("employee", employeeFullName);
            parameters.put("customer", customerFullname);
            
            String reportPath = "C:\\Users\\euris\\Escritorio\\NetBeans Projects\\SistemaFacturacion\\src\\Views\\Reports\\NuevaFactura.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(soldItems));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
