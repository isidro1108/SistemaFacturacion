/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.IFacturaController;
import Entities.ArticuloFactura;
import Entities.Factura;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author euris
 */
public class FacturaController implements IFacturaController {
    private final DataContext dbContext;
    
    public FacturaController() {
        this.dbContext = new DataContext();
    }
    
    @Override
    public List<Factura> getAll() {
        Connection connection = dbContext.connect();
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM invoice ORDER BY id ASC";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {
                Factura factura = new Factura();
                
                factura.setId(result.getInt("id"));
                factura.setIdCustomer(result.getInt("id_customer"));
                factura.setIdEmployee(result.getInt("id_employee"));
                factura.setCode(result.getString("code"));
                factura.setRnc(result.getString("rnc"));
                factura.setNif(result.getString("nif"));
                factura.setInvoiceType(result.getString("invoice_type"));
                factura.setPaymentType(result.getString("payment_type"));
                factura.setCash(result.getFloat("cash"));
                factura.setCreationDate(result.getDate("creation_date"));
                
                String sqlSoldItems = "SELECT * FROM sold_item WHERE id_invoice =" + factura.getId();
                Statement statementSoldItem = connection.createStatement();
                ResultSet resultSoldItem = statementSoldItem.executeQuery(sqlSoldItems);
                
                while (resultSoldItem.next()) {
                    ArticuloFactura soldItem = new ArticuloFactura();
                    
                    soldItem.setId(resultSoldItem.getInt("id"));
                    soldItem.setIdItem(resultSoldItem.getInt("id_item"));
                    soldItem.setIdInvoice(resultSoldItem.getInt("id_invoice"));
                    soldItem.setCode(resultSoldItem.getString("code"));
                    soldItem.setName(resultSoldItem.getString("name"));
                    soldItem.setQuantity(resultSoldItem.getInt("quantity"));
                    soldItem.setSale(resultSoldItem.getFloat("sale"));
                    soldItem.setItbis(resultSoldItem.getFloat("itbis"));
                    soldItem.setSubTotal(resultSoldItem.getFloat("sub_total"));
                    factura.addSoldItems(soldItem);
                }
                facturas.add(factura);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return facturas;
    }

    @Override
    public Factura getById(int id) {
        Connection connection = dbContext.connect();
        Factura factura = new Factura();
        String sql = "SELECT * FROM invoice WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {                
                factura.setId(result.getInt("id"));
                factura.setIdCustomer(result.getInt("id_customer"));
                factura.setIdEmployee(result.getInt("id_employee"));
                factura.setCode(result.getString("code"));
                factura.setRnc(result.getString("rnc"));
                factura.setNif(result.getString("nif"));
                factura.setInvoiceType(result.getString("invoice_type"));
                factura.setPaymentType(result.getString("payment_type"));
                factura.setCash(result.getFloat("cash"));
                factura.setCreationDate(result.getDate("creation_date"));
                
                String sqlSoldItems = "SELECT * FROM sold_item WHERE id_invoice =" + factura.getId();
                Statement statementSoldItem = connection.createStatement();
                ResultSet resultSoldItem = statementSoldItem.executeQuery(sqlSoldItems);
                
                while (resultSoldItem.next()) {
                    ArticuloFactura soldItem = new ArticuloFactura();
                    
                    soldItem.setId(resultSoldItem.getInt("id"));
                    soldItem.setIdItem(resultSoldItem.getInt("id_item"));
                    soldItem.setIdInvoice(resultSoldItem.getInt("id_invoice"));
                    soldItem.setCode(resultSoldItem.getString("code"));
                    soldItem.setName(resultSoldItem.getString("name"));
                    soldItem.setQuantity(resultSoldItem.getInt("quantity"));
                    soldItem.setSale(resultSoldItem.getFloat("sale"));
                    soldItem.setItbis(resultSoldItem.getFloat("itbis"));
                    soldItem.setSubTotal(resultSoldItem.getFloat("sub_total"));
                    factura.addSoldItems(soldItem);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Esta factura no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return factura;
    }
    
    @Override
    public int create(Factura factura) {
        Connection connection = dbContext.connect();
        String sql = "INSERT INTO invoice (id_customer, id_employee, code, rnc, nif, invoice_type, payment_type, cash, creation_date) "
                + "VALUES (" + factura.getIdCustomer() + ", "
                + factura.getIdEmployee() + ", "
                + "'" + factura.getCode() + "', "
                + "'" + factura.getRnc() + "', "
                + "'" + factura.getNif() + "', "
                + "'" + factura.getInvoiceType() + "', "
                + "'" + factura.getPaymentType() + "', "
                + factura.getCash() + ", "
                + "'" + factura.getCreationDate() + "') RETURNING id";
        int idInvoice = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) idInvoice = result.getInt("id");
            
            for (ArticuloFactura articuloFactura: factura.getSoldItems()) {
                String sqlSoldItem = "INSERT INTO sold_item (id_item, id_invoice, code, name, quantity, sale, itbis, sub_total) "
                                + "VALUES (" + articuloFactura.getIdItem() + ", "
                                + idInvoice + ", "
                                + "'" + articuloFactura.getCode() + "', "
                                + "'" + articuloFactura.getName() + "', "
                                + articuloFactura.getQuantity() + ", "
                                + articuloFactura.getSale() + ", "
                                + articuloFactura.getItbis() + ", "
                                + articuloFactura.getSubTotal() + ")";
                Statement statementSoldItem = connection.createStatement();
                statementSoldItem.execute(sqlSoldItem);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return idInvoice;
    }

    @Override
    public void update(Factura factura) {
        Connection connection = dbContext.connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE invoice SET "
                    + "id_customer=" + factura.getIdCustomer()+ ", "
                    + "id_employee=" + factura.getIdEmployee()+ ", "
                    + "code='" + factura.getCode()+ "', "
                    + "rnc='" + factura.getRnc()+ "', "
                    + "nif='" + factura.getNif()+ "', "
                    + "invoice_type='" + factura.getInvoiceType() + "', "
                    + "payment_type='" + factura.getPaymentType() + "', "
                    + "cash=" + factura.getCash() + ", "
                    + "creation_date='" + factura.getCreationDate() + "'"
                    + " WHERE id=" + factura.getId();
            
            statement.execute(sql);
            
            for (ArticuloFactura soldItem: factura.getSoldItems()) {
                Statement statementSoldItem = connection.createStatement();
                String sqlSoldItem = "UPDATE sold_item SET "
                                + "id_item=" + soldItem.getIdItem()+ ", "
                                + "id_invoice=" + soldItem.getIdInvoice()+ ", "
                                + "code='" + soldItem.getCode()+ "', "
                                + "name='" + soldItem.getName()+ "', "
                                + "quantity=" + soldItem.getQuantity()+ ", "
                                + "sale=" + soldItem.getSale()+ ", "
                                + "itbis=" + soldItem.getItbis() + ", "
                                + "sub_total=" + soldItem.getSubTotal()
                                + " WHERE id=" + soldItem.getId();
                statementSoldItem.execute(sqlSoldItem);
            }
            
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = dbContext.connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM invoice WHERE id=" + id;
            statement.execute(sql);
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
