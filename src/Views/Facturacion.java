/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Controllers.ArticuloController;
import Controllers.ClienteController;
import Controllers.EmpleadoController;
import Entities.Articulo;
import Entities.ArticuloFactura;
import Entities.Cliente;
import Entities.Empleado;
import Views.Constants.Constants;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author euris
 */
public class Facturacion extends javax.swing.JFrame {
    private final ClienteController clienteController;
    private final EmpleadoController empleadoController;
    private final ArticuloController articuloController;
    private final List<Cliente> clientes;
    private final List<Empleado> empleados;
    private final List<Articulo> articulos;
    private final List<ArticuloFactura> articulosFactura;
    private float generalSubTotal;
    private float generalItbis;
    private float generalTotal;
    private float efectivo;
    private final DecimalFormat customFormatPrices;
    
    /**
     * Creates new form Facturacion
     */
    public Facturacion() {
        this.clienteController = new ClienteController();
        this.empleadoController = new EmpleadoController();
        this.articuloController = new ArticuloController();
        this.clientes = this.clienteController.getAll();
        this.empleados = this.empleadoController.getAll();
        this.articulos = this.articuloController.getAll();
        this.articulosFactura = new ArrayList<>();
        this.generalSubTotal = 0;
        this.generalItbis = 0;
        this.generalTotal = 0;
        this.efectivo = 0;
        this.customFormatPrices = new DecimalFormat("$ ,##0.00");
        
        initComponents();
        setTitle("Nueva Factura");
        setLocationRelativeTo(null);
        setResizable(false);
        getAllCustomers();
        getAllEmployees();
        getAllItems();
        disappearComponents();
        txtFecha.setDate(new Date());
        jPanel1.setBackground(Constants.Colors.LIGHT_BLUE);
        jPanel2.setBackground(Constants.Colors.DARK_BLUE);
    }
    
    private void getAllCustomers() {
        this.clientes.forEach(cliente -> {
            jComboCliente.addItem(cliente.getName() + " " + cliente.getLastName());
        });
    }
    
    private void getAllEmployees() {
        this.empleados.forEach(empleado -> {
            jComboVendedor.addItem(empleado.getName() + " " + empleado.getLastName());
        });
    }
    
    private void getAllItems() {
        this.articulos.forEach(articulo -> {
            jComboArticulo.addItem(articulo.getName());
        });
    }
    
    private void disappearComponents() {
        labelTipoPago.setVisible(false);
        jComboTipoPago.setVisible(false);
        labelEfectivo.setVisible(false);
        txtEfectivo.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        labelResultadoEfectivo.setVisible(false);
        labelDevuelta.setVisible(false);
        btnInsertarEfectivo.setVisible(false);
    }
    
    private ArticuloFactura parseToArticuloFactura(Articulo articulo, int cantidad) {
        ArticuloFactura articuloFactura = new ArticuloFactura();
        float itbis = cantidad * (articulo.getSalePrice() * (articulo.getItbis()/100));
        float subTotal = cantidad * (articulo.getSalePrice() * (1 - (articulo.getItbis()/100)));
        float total = itbis + subTotal;
        
        articuloFactura.setCode(articulo.getCode());
        articuloFactura.setName(articulo.getName());
        articuloFactura.setQuantity(cantidad);
        articuloFactura.setSale(articulo.getSalePrice());
        articuloFactura.setItbis(itbis);
        articuloFactura.setSubTotal(subTotal);
        articuloFactura.setTotal(total);
        
        return articuloFactura;
    }
    
    private void addRowToTable(DefaultTableModel model, ArticuloFactura articuloFactura) {
        DecimalFormat customFormat = new DecimalFormat("0.00");
        float subTotal = articuloFactura.getSubTotal();
        float itbis = articuloFactura.getItbis();
        float total = articuloFactura.getTotal();
        
        model.addRow(new Object[] {
            articuloFactura.getCode(),
            articuloFactura.getName(),
            articuloFactura.getQuantity(),
            customFormat.format(articuloFactura.getSale()),
            customFormat.format(itbis),
            customFormat.format(subTotal),
            customFormat.format(total)
        });
        
        this.articulosFactura.add(articuloFactura);
        
        this.generalSubTotal+= subTotal;
        this.generalItbis+= itbis;
        this.generalTotal+= total;
        labelSubTotal.setText(this.customFormatPrices.format(this.generalSubTotal));
        labelTotalItbis.setText(this.customFormatPrices.format(this.generalItbis));
        labelTotal.setText(this.customFormatPrices.format(this.generalTotal));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTelefono = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jComboCliente = new javax.swing.JComboBox<>();
        jComboVendedor = new javax.swing.JComboBox<>();
        jComboTipoFactura = new javax.swing.JComboBox<>();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFactura = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelSubTotal = new javax.swing.JLabel();
        labelTotalItbis = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        labelResultadoEfectivo = new javax.swing.JLabel();
        labelDevuelta = new javax.swing.JLabel();
        jComboTipoPago = new javax.swing.JComboBox<>();
        labelTipoPago = new javax.swing.JLabel();
        labelEfectivo = new javax.swing.JLabel();
        txtEfectivo = new javax.swing.JTextField();
        btnImprimir = new javax.swing.JButton();
        jComboArticulo = new javax.swing.JComboBox<>();
        btnSalir = new javax.swing.JButton();
        btnInsertarEfectivo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        txtTelefono.setEnabled(false);

        txtEmail.setEnabled(false);

        jComboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione el cliente" }));
        jComboCliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboClienteItemStateChanged(evt);
            }
        });

        jComboVendedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione el vendedor" }));

        jComboTipoFactura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Crédito", "Al Contado" }));
        jComboTipoFactura.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboTipoFacturaItemStateChanged(evt);
            }
        });

        txtFecha.setDateFormatString("dd/MM/yyyy");
        txtFecha.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Cliente:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Teléfono:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Fecha:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Vendedor:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Tipo de Factura:");

        jTableFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Cantidad", "Precio Unidad", "ITBIS", "Sub Total", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableFactura);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Sub Total");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total ITBIS");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total");

        labelSubTotal.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        labelSubTotal.setText("$ 0.00");

        labelTotalItbis.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelTotalItbis.setForeground(new java.awt.Color(255, 255, 255));
        labelTotalItbis.setText("$ 0.00");

        labelTotal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTotal.setForeground(new java.awt.Color(255, 255, 255));
        labelTotal.setText("$ 0.00");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Efectivo");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Devuelta");

        labelResultadoEfectivo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelResultadoEfectivo.setForeground(new java.awt.Color(255, 255, 255));
        labelResultadoEfectivo.setText("$ 0.00");

        labelDevuelta.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelDevuelta.setForeground(new java.awt.Color(255, 255, 255));
        labelDevuelta.setText("$ 0.00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(labelTotalItbis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelResultadoEfectivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDevuelta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelSubTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelTotalItbis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(labelTotal))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(labelResultadoEfectivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(labelDevuelta))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jComboTipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta" }));
        jComboTipoPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboTipoPagoItemStateChanged(evt);
            }
        });

        labelTipoPago.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTipoPago.setText("Tipo de pago:");

        labelEfectivo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelEfectivo.setText("Efectivo:");

        txtEfectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEfectivoKeyPressed(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(0, 0, 255));
        btnImprimir.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimir.setText("Imprimir");

        jComboArticulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un artículo" }));
        jComboArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboArticuloActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(0, 0, 255));
        btnSalir.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnInsertarEfectivo.setBackground(new java.awt.Color(0, 0, 255));
        btnInsertarEfectivo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnInsertarEfectivo.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertarEfectivo.setText("Insertar");
        btnInsertarEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarEfectivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 815, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelTipoPago)
                                    .addComponent(labelEfectivo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtEfectivo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnInsertarEfectivo))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(696, 696, 696)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboTipoFactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTipoPago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEfectivo)
                    .addComponent(btnInsertarEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir)
                    .addComponent(btnSalir)
                    .addComponent(jComboArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboClienteItemStateChanged
        int selectedIndex = jComboCliente.getSelectedIndex();
        if (selectedIndex > 0) {
            Cliente cliente = this.clientes.get(selectedIndex - 1);
            txtEmail.setText(cliente.getEmail());
            txtTelefono.setText(cliente.getPhone());
        } else {
            txtEmail.setText("");
            txtTelefono.setText("");
        }
    }//GEN-LAST:event_jComboClienteItemStateChanged

    private void jComboTipoFacturaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboTipoFacturaItemStateChanged
        boolean isAlContado = evt.getItem().equals("Al Contado");
        labelTipoPago.setVisible(isAlContado);
        jComboTipoPago.setVisible(isAlContado);
        labelEfectivo.setVisible(isAlContado);
        txtEfectivo.setVisible(isAlContado);
        jLabel10.setVisible(isAlContado);
        jLabel11.setVisible(isAlContado);
        labelResultadoEfectivo.setVisible(isAlContado);
        labelDevuelta.setVisible(isAlContado);
        btnInsertarEfectivo.setVisible(isAlContado);
        
        if (!isAlContado) {
            labelResultadoEfectivo.setText("$ 0.00");
            labelDevuelta.setText("$ 0.00");
            this.efectivo = 0;
        }
    }//GEN-LAST:event_jComboTipoFacturaItemStateChanged

    private void jComboTipoPagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboTipoPagoItemStateChanged
        boolean isEfectivo = evt.getItem().equals("Efectivo");
        labelEfectivo.setVisible(isEfectivo);
        txtEfectivo.setVisible(isEfectivo);
        jLabel10.setVisible(isEfectivo);
        jLabel11.setVisible(isEfectivo);
        labelResultadoEfectivo.setVisible(isEfectivo);
        labelDevuelta.setVisible(isEfectivo);
        btnInsertarEfectivo.setVisible(isEfectivo);
        
        if (!isEfectivo) {
            labelResultadoEfectivo.setText("$ 0.00");
            labelDevuelta.setText("$ 0.00");
            this.efectivo = 0;
        }
    }//GEN-LAST:event_jComboTipoPagoItemStateChanged

    private void jComboArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboArticuloActionPerformed
        int selectedIndex = jComboArticulo.getSelectedIndex();
        if (selectedIndex > 0) {
            if (this.efectivo == 0) {
                String cantidad = JOptionPane.showInputDialog(null, "Ingrese la cantidad", "Cantidad", JOptionPane.DEFAULT_OPTION);
                try {
                    int cantidadToInt = Integer.parseInt(cantidad);
                    DefaultTableModel model = (DefaultTableModel)jTableFactura.getModel();
                    Articulo articulo = this.articulos.get(selectedIndex - 1);
                    ArticuloFactura articuloFactura = parseToArticuloFactura(articulo, cantidadToInt);

                    addRowToTable(model, articuloFactura);

                    jTableFactura.setModel(model);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Solo se pueden poner números enteros", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ya el efectivo ha sido ingresado, no puede agregar más artículos a la factura", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jComboArticuloActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnInsertarEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarEfectivoActionPerformed
        if (!this.articulosFactura.isEmpty()) {
            try {
                float efectivoToFloat = Float.parseFloat(txtEfectivo.getText());

                if (efectivoToFloat >= this.generalTotal) {
                    this.efectivo = efectivoToFloat;
                    labelResultadoEfectivo.setText(this.customFormatPrices.format(this.efectivo));
                    labelDevuelta.setText(this.customFormatPrices.format(this.efectivo - this.generalTotal));
                    txtEfectivo.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "El efectivo es inferior al monto a pagar", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Entrada inválida, inserte el efectivo nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay artículos en la factura", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnInsertarEfectivoActionPerformed

    private void txtEfectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEfectivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnInsertarEfectivo.doClick();
        }
    }//GEN-LAST:event_txtEfectivoKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Facturacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnInsertarEfectivo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> jComboArticulo;
    private javax.swing.JComboBox<String> jComboCliente;
    private javax.swing.JComboBox<String> jComboTipoFactura;
    private javax.swing.JComboBox<String> jComboTipoPago;
    private javax.swing.JComboBox<String> jComboVendedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableFactura;
    private javax.swing.JLabel labelDevuelta;
    private javax.swing.JLabel labelEfectivo;
    private javax.swing.JLabel labelResultadoEfectivo;
    private javax.swing.JLabel labelSubTotal;
    private javax.swing.JLabel labelTipoPago;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelTotalItbis;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextField txtEmail;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
