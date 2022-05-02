/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Controllers.EmpleadoController;
import Entities.CustomResponses.FormStatus;
import Entities.Empleado;
import Views.Constants.Constants;
import com.toedter.calendar.JDateChooser;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author euris
 */
public class RegistroEmpleados extends javax.swing.JFrame {
    private final EmpleadoController empleadoController;
    private final FormStatus formStatus;
    private final List<Integer> idsEmpleados;
    private Empleado empleado;
    private int idToAdd;
    private int indexRowToModify;
    
    /**
     * Creates new form RegistroArticulos
     */
    public RegistroEmpleados() {
        this.empleadoController = new EmpleadoController();
        this.formStatus = new FormStatus();
        this.idsEmpleados = new ArrayList<>();
        this.empleado = new Empleado();
        this.idToAdd = 0;
        this.indexRowToModify = -1;
        
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Empleados");
        initTable();
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
        List<Empleado> empleados = this.empleadoController.getAll();
        setColumns(model);
        empleados.forEach(emp -> {
            addRowToTable(model, emp);
        });
        jTableClientes.setModel(model);
    }
    
    private void updateTable(Empleado empleado) {
        if (empleado.getId() > 0) {
            updateRow(empleado);
        } else {
            DefaultTableModel model = (DefaultTableModel) jTableClientes.getModel();
            addRowToTable(model, empleado);
            jTableClientes.setModel(model);
        }
    }
    
    private void updateRow(Empleado empleado) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat customFormat = new DecimalFormat("0.00");
        Object[] empleadoData = new Object[] {
            empleado.getName(),
            empleado.getLastName(),
            empleado.getAddress(),
            empleado.getPhone(),
            empleado.getIdentityCard(),
            empleado.getEmail(),
            empleado.getSalary(),
            dateFormat.format(empleado.getDateOfBirth()),
            dateFormat.format(empleado.getDateOfAdmission()),
            customFormat.format(empleado.getSales())
        };
        
        for (int i = 0; i < empleadoData.length; i++)
            jTableClientes.setValueAt(empleadoData[i], indexRowToModify, i);
    }
    
    private void addRowToTable(DefaultTableModel model, Empleado empleado) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat customFormat = new DecimalFormat("0.00");
        int id = empleado.getId();
        this.idsEmpleados.add(id > 0 ? id : this.idToAdd);
        model.addRow(new Object[] {
            empleado.getName(),
            empleado.getLastName(),
            empleado.getAddress(),
            empleado.getPhone(),
            empleado.getIdentityCard(),
            empleado.getEmail(),
            empleado.getSalary(),
            dateFormat.format(empleado.getDateOfBirth()),
            dateFormat.format(empleado.getDateOfAdmission()),
            customFormat.format(empleado.getSales())
        });
    }
    
    private void setColumns(DefaultTableModel model) {
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Dirección");
        model.addColumn("Teléfono");
        model.addColumn("Cédula");
        model.addColumn("Correo electrónico");
        model.addColumn("Sueldo");
        model.addColumn("Fecha de nacimiento");
        model.addColumn("Fecha de admisión");
        model.addColumn("ventas");
    }
    
    
    private void setFormStatus() {               
        Object[] fields = new Object[] { txtName, txtLastName, txtAddress, txtPhone, txtIdentityCard, txtEmail, txtSalary, txtDateOfBirth }; 
        
        for (Object field : fields) {
            try {
                validateField((JTextField)field);
            } catch (Exception ex) {
                validateField((JDateChooser)field);
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
    
    private void validateField(JDateChooser jDateChooser) {
        if (jDateChooser.getDate() == null) {
            if (formStatus.isValid()) formStatus.setValid(false);
            formStatus.addNameInvalidField(jDateChooser.getName());
        }
    }
    
    private void clearForm() {
        txtName.setText("");
        txtLastName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtIdentityCard.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
        txtDateOfBirth.setCalendar(null);
    }
    
    private void toggleButtons(boolean inEdition) {
        btnAgregar.setEnabled(!inEdition);
        btnModificar.setEnabled(!inEdition);
        btnEliminar.setEnabled(!inEdition);
        btnSalir.setEnabled(!inEdition);
        btnGuardarCambios.setVisible(inEdition);
        btnCancelar.setVisible(inEdition);
    }
    
    private boolean saveOrUpdateArticulo() {
        setFormStatus();
        if (formStatus.isValid()) {             
            empleado.setName(txtName.getText());
            empleado.setLastName(txtLastName.getText());
            empleado.setAddress(txtAddress.getText());
            empleado.setIdentityCard(txtIdentityCard.getText());
            empleado.setPhone(txtPhone.getText());
            empleado.setEmail(txtEmail.getText());
            empleado.setSales(0);
            empleado.setDateOfAdmission(new Date());
            
            try {
                empleado.setDateOfBirth(txtDateOfBirth.getDate());
                try {
                    empleado.setSalary(Float.parseFloat(txtSalary.getText()));
                    if (empleado.getId() > 0) empleadoController.update(empleado);
                    else this.idToAdd = empleadoController.create(empleado);
                    updateTable(empleado);
                    clearForm();
                    JOptionPane.showMessageDialog(null, "Empleado " + (empleado.getId() > 0 ? "modificado" : "agregado") + " con éxito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    formStatus.clearStatus();
                    return true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Debe insertar números enteros o decimales en el campo \"Sueldo\"", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Fecha inválida", "Error", JOptionPane.ERROR_MESSAGE);
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
        jTableClientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDateOfBirth = new com.toedter.calendar.JDateChooser();
        txtIdentityCard = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSalary = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardarCambios = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableClientes);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Registro de Empleados");

        txtName.setName("Nombres"); // NOI18N

        txtLastName.setName("Apellidos"); // NOI18N

        txtPhone.setName("Teléfono"); // NOI18N

        txtEmail.setName("Correo electrónico"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Fecha de nacimiento:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Correo electrónico:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Apellidos:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombres:");

        txtAddress.setName("Dirección"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Teléfono:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Dirección:");

        txtDateOfBirth.setDateFormatString("dd/MM/yyyy");
        txtDateOfBirth.setName("Fecha de nacimiento"); // NOI18N

        txtIdentityCard.setName("Cédula"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Cédula:");

        txtSalary.setName("Sueldo"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sueldo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(txtLastName)
                    .addComponent(txtPhone)
                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtName)
                    .addComponent(txtDateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIdentityCard)
                    .addComponent(txtSalary))
                .addGap(43, 43, 43))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdentityCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
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
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarCambios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(159, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        int[] rowIndexes = jTableClientes.getSelectedRows();
        int length = rowIndexes.length;
        int firstIndex = length > 0 ? rowIndexes[0] : 0;
        String message = length == 0 ? "No hay empleados seleccionados"
                : length == 1 ? "1 empleado eliminado"
                : length + " empleados eliminados";
        String confirmMessage = length == 1
                ? "¿Desea eliminar el empleado seleccionado?"
                : "¿Desea eliminar los empleados seleccionados?";
        
        int confirm = length > 0 
                ? JOptionPane.showConfirmDialog(null, confirmMessage, "Seleccione una opción", JOptionPane.YES_NO_OPTION) : -1; 
        
        if (confirm == 0) {
            for (int i = 0; i < rowIndexes.length; i++) {
                int idEmpleado = idsEmpleados.get(firstIndex);
                DefaultTableModel model = (DefaultTableModel)jTableClientes.getModel();

                model.removeRow(firstIndex);
                idsEmpleados.remove(firstIndex);
                empleadoController.delete(idEmpleado);
            }
        }
        if (confirm == 0 || confirm == -1)
            JOptionPane.showMessageDialog(null, message);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int[] indexes = jTableClientes.getSelectedRows();
        int length = indexes.length;
        if (length == 1) {
            this.indexRowToModify = indexes[0];
            int idEmpleado = this.idsEmpleados.get(indexRowToModify);
            this.empleado = this.empleadoController.getById(idEmpleado);
                        
            txtName.setText(empleado.getName());
            txtLastName.setText(empleado.getLastName());
            txtAddress.setText(empleado.getAddress());
            txtPhone.setText(empleado.getPhone());
            txtIdentityCard.setText(empleado.getIdentityCard());
            txtEmail.setText(empleado.getEmail());
            txtSalary.setText(String.valueOf(empleado.getSalary()));
            txtDateOfBirth.setDate(empleado.getDateOfBirth());
            toggleButtons(true);
        } else {
            String message = length == 0 
                    ? "No hay ningún empleado seleccionado"
                    : "Solo se puede seleccionar un solo empleado para modificarlo";
            
            JOptionPane.showMessageDialog(null, message);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        if (saveOrUpdateArticulo()) {
            toggleButtons(false);
            this.empleado = new Empleado();
        }
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        toggleButtons(false);
        this.empleado = new Empleado();
        clearForm();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextField txtAddress;
    private com.toedter.calendar.JDateChooser txtDateOfBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdentityCard;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSalary;
    // End of variables declaration//GEN-END:variables
}
