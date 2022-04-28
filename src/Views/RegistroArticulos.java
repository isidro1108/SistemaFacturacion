/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Controllers.ArticuloController;
import Controllers.GenericEntityController;
import Entities.Articulo;
import Entities.CustomResponses.FormStatus;
import Entities.GenericEntity;
import Views.Constants.Constants;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author euris
 */
public class RegistroArticulos extends javax.swing.JFrame {
    private final ArticuloController articuloController;
    private final GenericEntityController tipoArticuloController;
    private final FormStatus formStatus;
    private final List<Integer> idsArticulos;
    private final List<GenericEntity> tipoArticulos;
    private Articulo articulo;
    private int idToAdd;
    private int indexRowToModify;
    
    /**
     * Creates new form RegistroArticulos
     */
    public RegistroArticulos() {
        this.articuloController = new ArticuloController();
        this.tipoArticuloController = new GenericEntityController("item_type");
        this.formStatus = new FormStatus();
        this.idsArticulos = new ArrayList<>();
        this.tipoArticulos = this.tipoArticuloController.getAll();
        this.articulo = new Articulo();
        this.idToAdd = 0;
        this.indexRowToModify = -1;
        
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Artículos");
        initTable();
        getTipoArticulos();
        jPanel1.setBackground(Constants.Colors.LIGHT_BLUE);
        jPanel2.setBackground(Constants.Colors.DARK_BLUE);
        jLabel1.setForeground(Constants.Colors.DARK_BLUE);
        btnGuardarCambios.setVisible(false);
        btnCancelar.setVisible(false);
    }
    
    private void initTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        List<Articulo> articulos = articuloController.getAll();
        setColumns(model);
        articulos.forEach(art -> {
            addRowToTable(model, art);
        });
        jTableArticulos.setModel(model);
    }
    
    private void getTipoArticulos() {
        this.tipoArticulos.forEach(tipoArticulo -> {
            cBoxTipoArticulo.addItem(tipoArticulo.getName());
        });
    }
    
    private void updateTable(Articulo articulo) {
        if (articulo.getId() > 0) {
            updateRow(articulo);
        } else {
            DefaultTableModel model = (DefaultTableModel) jTableArticulos.getModel();
            addRowToTable(model, articulo);
            jTableArticulos.setModel(model);
        }
    }
    
    private void updateRow(Articulo articulo) {
        DecimalFormat customFormat = new DecimalFormat("#.00");
        String purchasePrice = customFormat.format(articulo.getPurchasePrice());
        String salePrice = customFormat.format(articulo.getSalePrice());
        GenericEntity tipoArticulo = this.tipoArticuloController.getById(articulo.getIdItemType());
        Object[] articuloData = new Object[] {
            articulo.getCode(),
            articulo.getName(),
            tipoArticulo.getName(),
            articulo.getDescription(),
            articulo.getQuantity(),
            purchasePrice,
            salePrice,
            articulo.getReorderPoint(),
            articulo.getItbis()
        };
        
        for (int i = 0; i < articuloData.length; i++)
            jTableArticulos.setValueAt(articuloData[i], indexRowToModify, i);
    }
    
    private void addRowToTable(DefaultTableModel model, Articulo articulo) {
        DecimalFormat customFormat = new DecimalFormat("#.00");
        String purchasePrice = customFormat.format(articulo.getPurchasePrice());
        String salePrice = customFormat.format(articulo.getSalePrice());
        GenericEntity tipoArticulo = this.tipoArticuloController.getById(articulo.getIdItemType());
        int id = articulo.getId();
        idsArticulos.add(id > 0 ? id : this.idToAdd);
        model.addRow(new Object[] {
            articulo.getCode(),
            articulo.getName(),
            tipoArticulo.getName(),
            articulo.getDescription(),
            articulo.getQuantity(),
            purchasePrice,
            salePrice,
            articulo.getReorderPoint(),
            articulo.getItbis()
        });
    }
    
    private void setColumns(DefaultTableModel model) {
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Tipo de artículo");
        model.addColumn("Descripción");
        model.addColumn("Cantidad");
        model.addColumn("Precio de compra");
        model.addColumn("Precio de venta");
        model.addColumn("Punto de reorden");
        model.addColumn("ITBIS");
    }
    
    
    private void setFormStatus() {               
        Object[] fields = new Object[] { txtCode, txtName, txtDescription, txtQuantity, txtPurchasePrice, txtSalePrice, txtReorderPoint, txtItbis }; 
        
        for (Object field : fields) {
            try {
                validateField((JTextField)field);
            } catch (Exception ex) {
                validateField((JTextArea)field);
            }
        }
        
        List<String> nameInvalidFields = formStatus.getNameInvalidFields();
        int size = nameInvalidFields.size();
        if (size == 1) {
            formStatus.setMessage("Debe llenar el campo " + nameInvalidFields.get(0));
        } else if (size > 1) {
            int i = 0;
            String message = "Debe llenar los campos ";
            char firstLetterLastWord = nameInvalidFields.get(size - 1).charAt(0);
            String lastSeparator = "i".equals(String.valueOf(firstLetterLastWord).toLowerCase()) ? " e " : " y ";
            for (String nameInvalidField : nameInvalidFields) {
                String separator = i == size - 1 
                        ? "." : i == size - 2
                        ? lastSeparator : ", ";
                message+= "\"" + nameInvalidField + "\"" + separator;
                i++;
            }
            formStatus.setMessage(message);
        }
    }
    
    private void validateField(JTextField jTextField) {
        if ("".equals(jTextField.getText())) {
            if (formStatus.isValid()) formStatus.setValid(false);
            formStatus.addNameInvalidField(jTextField.getName());
        }
    }
    
    private void validateField(JTextArea jTextArea) {
        if ("".equals(jTextArea.getText())) {
            if (formStatus.isValid()) formStatus.setValid(false);
            formStatus.addNameInvalidField(jTextArea.getName());
        }
    }
    
    private void clearForm() {
        txtCode.setText("");
        txtName.setText("");
        cBoxTipoArticulo.setSelectedIndex(0);
        txtDescription.setText("");
        txtQuantity.setText("");
        txtPurchasePrice.setText("");
        txtSalePrice.setText("");
        txtReorderPoint.setText("");
        txtItbis.setText("");
    }
    
    private void toggleButtons(boolean inEdition) {
        btnAgregar.setEnabled(!inEdition);
        btnAgregarNuevoTipoArticulo.setEnabled(!inEdition);
        btnModificar.setEnabled(!inEdition);
        btnEliminar.setEnabled(!inEdition);
        btnSalir.setEnabled(!inEdition);
        btnGuardarCambios.setVisible(inEdition);
        btnCancelar.setVisible(inEdition);
    }
    
    private int getIndexTipoArticulo(Articulo articulo) {
        int i = 0;
        for (GenericEntity tipoArticulo : this.tipoArticulos) {
            if (tipoArticulo.getId() == articulo.getIdItemType()) return i; 
            i++;
        }
        return i;
    }
    
    private boolean saveOrUpdateArticulo() {
        setFormStatus();
        if (formStatus.isValid()) { 
            GenericEntity tipoArticulo = this.tipoArticulos.get(cBoxTipoArticulo.getSelectedIndex());
            int idTipoArticulo = tipoArticulo.getId();
            
            articulo.setCode(txtCode.getText());
            articulo.setName(txtName.getText());
            articulo.setIdItemType(idTipoArticulo);
            articulo.setDescription(txtDescription.getText());
            try {
                articulo.setQuantity(Integer.parseInt(txtQuantity.getText()));
                articulo.setReorderPoint(Integer.parseInt(txtReorderPoint.getText()));
                try {
                    articulo.setPurchasePrice(Float.parseFloat(txtPurchasePrice.getText()));
                    articulo.setSalePrice(Float.parseFloat(txtSalePrice.getText()));
                    articulo.setItbis(Float.parseFloat(txtItbis.getText()));
                    if (articulo.getId() > 0) articuloController.update(articulo);
                    else this.idToAdd = articuloController.create(articulo);
                    updateTable(articulo);
                    clearForm();
                    formStatus.clearStatus();
                    JOptionPane.showMessageDialog(null, "Artículo " + (articulo.getId() > 0 ? "modificado" : "agregado") + " con éxito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Debe insertar números enteros o decimales en los campos \"Precio de compra\", \"Precio de venta\" e \"ITBIS\"", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe insertar números enteros en los campos \"Cantidad\" y \"Punto de reorden\"", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, formStatus.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
        formStatus.clearStatus();
        return false;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableArticulos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtCode = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        txtPurchasePrice = new javax.swing.JTextField();
        txtSalePrice = new javax.swing.JTextField();
        txtReorderPoint = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cBoxTipoArticulo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtItbis = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardarCambios = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAgregarNuevoTipoArticulo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableArticulos);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Registro de Artículos");

        txtCode.setName("Código"); // NOI18N

        txtName.setName("Nombre"); // NOI18N

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        txtDescription.setName("Descripción"); // NOI18N
        jScrollPane2.setViewportView(txtDescription);

        txtPurchasePrice.setName("Precio de compra"); // NOI18N

        txtSalePrice.setName("Precio de venta"); // NOI18N

        txtReorderPoint.setName("Punto de reorden"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Punto de reorden:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Precio de venta:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Precio de compra:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Descripción:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Código:");

        txtQuantity.setName("Cantidad"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cantidad:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tipo de artículo:");

        txtItbis.setName("ITBIS"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ITBIS:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQuantity)
                            .addComponent(jScrollPane2)
                            .addComponent(txtName)
                            .addComponent(txtPurchasePrice)
                            .addComponent(txtSalePrice, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCode)
                            .addComponent(cBoxTipoArticulo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtItbis)
                            .addComponent(txtReorderPoint))))
                .addGap(43, 43, 43))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cBoxTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReorderPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItbis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        btnAgregar.setBackground(new java.awt.Color(0, 0, 255));
        btnAgregar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
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

        btnModificar.setBackground(new java.awt.Color(0, 0, 255));
        btnModificar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(0, 0, 255));
        btnEliminar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnGuardarCambios.setBackground(new java.awt.Color(0, 0, 255));
        btnGuardarCambios.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnGuardarCambios.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCambios.setText("Guardar Cambios");
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(0, 0, 255));
        btnCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAgregarNuevoTipoArticulo.setBackground(new java.awt.Color(0, 0, 255));
        btnAgregarNuevoTipoArticulo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnAgregarNuevoTipoArticulo.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarNuevoTipoArticulo.setText("Agregar nuevo tipo de artículo");
        btnAgregarNuevoTipoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNuevoTipoArticuloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarNuevoTipoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarCambios, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(167, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 84, Short.MAX_VALUE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarNuevoTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        saveOrUpdateArticulo();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int[] rowIndexes = jTableArticulos.getSelectedRows();
        int length = rowIndexes.length;
        int firstIndex = length > 0 ? rowIndexes[0] : 0;
        String message = length == 0 ? "No hay artículos seleccionados"
                : length == 1 ? "1 artículo eliminado"
                : length + " artículos eliminados";
        String confirmMessage = length == 1
                ? "¿Desea eliminar el artículo seleccionado?"
                : "¿Desea eliminar los artículos seleccionados?";
        
        int confirm = length > 0 
                ? JOptionPane.showConfirmDialog(null, confirmMessage, "Seleccione una opción", JOptionPane.YES_NO_OPTION) : -1; 
        
        if (confirm == 0) {
            for (int i = 0; i < rowIndexes.length; i++) {
                int idArticulo = idsArticulos.get(firstIndex);
                DefaultTableModel model = (DefaultTableModel)jTableArticulos.getModel();

                model.removeRow(firstIndex);
                idsArticulos.remove(firstIndex);
                articuloController.delete(idArticulo);
            }
        }
        if (confirm == 0 || confirm == -1)
            JOptionPane.showMessageDialog(null, message);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int[] indexes = jTableArticulos.getSelectedRows();
        int length = indexes.length;
        if (length == 1) {
            this.indexRowToModify = indexes[0];
            int idArticulo = this.idsArticulos.get(indexRowToModify);
            this.articulo = this.articuloController.getById(idArticulo);
            int indexTipoArticulo = getIndexTipoArticulo(this.articulo);
            
            txtCode.setText(articulo.getCode());
            txtName.setText(articulo.getName());
            cBoxTipoArticulo.setSelectedIndex(indexTipoArticulo);
            txtDescription.setText(articulo.getDescription());
            txtQuantity.setText(String.valueOf(articulo.getQuantity()));
            txtPurchasePrice.setText(String.valueOf(articulo.getPurchasePrice()));
            txtSalePrice.setText(String.valueOf(articulo.getSalePrice()));
            txtReorderPoint.setText(String.valueOf(articulo.getReorderPoint()));
            txtItbis.setText(String.valueOf(articulo.getItbis()));
            toggleButtons(true);
        } else {
            String message = length == 0 
                    ? "No hay ningún artículo seleccionado"
                    : "Solo se puede seleccionar un solo artículo para modificarlo";
            
            JOptionPane.showMessageDialog(null, message);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        if (saveOrUpdateArticulo()) {
            toggleButtons(false);
            this.articulo = new Articulo();
        }
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        toggleButtons(false);
        this.articulo = new Articulo();
        clearForm();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarNuevoTipoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNuevoTipoArticuloActionPerformed
        String nameItemType = JOptionPane.showInputDialog(null, "Nuevo tipo de artículo", "Tipo de artículo", JOptionPane.DEFAULT_OPTION);
        if (nameItemType != null) {
            if (!"".equals(nameItemType)) {
                GenericEntity tipoArticulo = new GenericEntity();
                tipoArticulo.setName(nameItemType);
                tipoArticulo.setId(this.tipoArticuloController.create(tipoArticulo));
                this.tipoArticulos.add(tipoArticulo);
                this.cBoxTipoArticulo.addItem(tipoArticulo.getName());
            } else {
                JOptionPane.showMessageDialog(null, "No se guardó ningún tipo de artículo");
            }
        }
    }//GEN-LAST:event_btnAgregarNuevoTipoArticuloActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroArticulos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarNuevoTipoArticulo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cBoxTipoArticulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableArticulos;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtItbis;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPurchasePrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtReorderPoint;
    private javax.swing.JTextField txtSalePrice;
    // End of variables declaration//GEN-END:variables
}
