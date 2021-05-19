/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.telas;

import java.sql.*;
import br.com.projeto.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Leonardo
 */
public class TelaGenerosPreferidos extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    int linhaAtual ;
    int colunaAtual;
    String valorSelecionado = "";
    private String nomeUsuario;
    private int idUsuario;
    
    /**
     * @return the nomeUsuario
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * @param nomeUsuario the nomeUsuario to set
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    /**
     * Creates new form TelaGenerosPreferidos
     */
    public TelaGenerosPreferidos() {
        initComponents();
        conexao = ModuloConexao.conector();
        setaNomeUsuario();
        setaIdUsuario();
        doBancoParaTabela();
        doBancoParaTabelaUsuario();
    }
    
    private void setaIdUsuario(){
        String query = "select id_usuario from tbusuarios where nome_usuario = ?";
        try {
            pst = conexao.prepareStatement(query);
            pst.setString(1,nomeUsuario);
            rs = pst.executeQuery();
            if(rs.next()){
                this.idUsuario = rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Id do usuário.");
        }
    }
    
    private void setaNomeUsuario(){
        String query = "select nome_usuario from usuarioAtual";
        try {
            pst = conexao.prepareStatement(query);
            rs = pst.executeQuery();
            if(rs.next()){
                this.nomeUsuario = rs.getString(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar nome do usuário.");
        }
    }
    
    private void doBancoParaTabelaUsuario(){
        String query = "select nome_genero,data_insercao from usuarioXgenero where id_usuario = ?";
        DefaultTableModel model = (DefaultTableModel)tblGenerosUsuario.getModel();
        model.setRowCount(0);
        try {
            pst = conexao.prepareStatement(query);
            pst.setString(1,Integer.toString(this.idUsuario));
            rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar gêneros do usuário.");
        }
    }
    
    
    private void doBancoParaTabela(){
       DefaultTableModel model = (DefaultTableModel)tblGenerosSistema.getModel();
       model.setRowCount(0);
       String query = "select * from tbgeneros";
       try {
            pst = conexao.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getString(2),
                    rs.getString(3)
                });
            }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Erro ao carregar gêneros para o programa");
       }
   }
    
    private void cadastrarGenero(){
       String novoGenero = txtGeneroNovo.getText();
       if(!novoGenero.equals("")){
           String query = "insert into tbgeneros (nome_genero) values (?)";
           try {
               pst = conexao.prepareStatement(query);
               pst.setString(1,txtGeneroNovo.getText());
               pst.executeUpdate();
               JOptionPane.showMessageDialog(null, "Gênero cadastrado com sucesso");
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Gênero já cadastrado.");
           }
       }else{
           JOptionPane.showMessageDialog(null, "Nenhum gênero inserido.");
       }
    }
    
    private void cadastraGeneroParaOUsuario(){
        String novoGenero = txtNovoGenero.getText();
        if(!novoGenero.equals("")){
            String query = "select * from usuarioXgenero where id_usuario=? and nome_genero=?";
            try {
               pst = conexao.prepareStatement(query);
               pst.setString(1,Integer.toString(this.idUsuario));
               pst.setString(2,txtNovoGenero.getText());
               rs = pst.executeQuery();
               if(rs.next()){
                   JOptionPane.showMessageDialog(null, "Gênero já cadastrado");
               }else{
                   query = "insert into usuarioXgenero(id_usuario,nome_genero) values (?,?)";
                   pst = conexao.prepareStatement(query);
                   pst.setString(1,Integer.toString(this.idUsuario));
                   pst.setString(2,txtNovoGenero.getText());
                   pst.executeUpdate();
                   txtNovoGenero.setText("");
                   JOptionPane.showMessageDialog(null, "Gênero inserido com sucesso.");
               }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Nenhum gênero digitado.");
        }
    }
   
    private void excluirDeMeusGeneros(){
        String novoGenero = txtNovoGenero.getText();
        if(!novoGenero.equals("")){
            String query = "select * from usuarioXgenero where id_usuario=? and nome_genero=?";
            try {
               pst = conexao.prepareStatement(query);
               pst.setString(1,Integer.toString(this.idUsuario));
               pst.setString(2,txtNovoGenero.getText());
               rs = pst.executeQuery();
               if(rs.next()){
                   query = "delete from usuarioXgenero where nome_genero = ?";
                   pst = conexao.prepareStatement(query);
                   pst.setString(1,txtNovoGenero.getText());
                   pst.executeUpdate();
                   txtNovoGenero.setText("");
                   JOptionPane.showMessageDialog(null, "Gênero excluído com sucesso.");
               }else{
                   JOptionPane.showMessageDialog(null, "Gênero não está cadastrado em Meus Gêneros");
               }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Nenhum gênero digitado.");
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btn_voltar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGenerosSistema = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGenerosUsuario = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNovoGenero = new javax.swing.JTextField();
        btnExcluirGenero = new javax.swing.JButton();
        btnCadastrarGeneroUsuario = new javax.swing.JButton();
        btnNovoGenero = new javax.swing.JButton();
        txtGeneroNovo = new javax.swing.JTextField();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela - Generos Preferidos");
        setPreferredSize(new java.awt.Dimension(553, 490));
        setResizable(false);

        btn_voltar.setText("Voltar");
        btn_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_voltarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Gêneros do Sistema"));

        tblGenerosSistema.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Gênero", "Data de Inserção"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGenerosSistema.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGenerosSistemaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGenerosSistema);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Meus Gêneros Cadastrados"));

        tblGenerosUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Gênero", "Data de Inserção"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblGenerosUsuario);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastrar/Excluir gêneros em \"Meus Gêneros\""));

        jLabel2.setText("Título do Gênero");

        btnExcluirGenero.setText("Excluir de Meus Gêneros");
        btnExcluirGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirGeneroActionPerformed(evt);
            }
        });

        btnCadastrarGeneroUsuario.setText("Cadastrar em Meus Gêneros");
        btnCadastrarGeneroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarGeneroUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNovoGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExcluirGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadastrarGeneroUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNovoGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnCadastrarGeneroUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluirGenero)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNovoGenero.setText("Cadastrar Novo Gênero");
        btnNovoGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoGeneroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btn_voltar)
                .addGap(32, 32, 32)
                .addComponent(txtGeneroNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNovoGenero)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_voltar)
                    .addComponent(btnNovoGenero)
                    .addComponent(txtGeneroNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(516, 550));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_voltarActionPerformed
        // TODO add your handling code here:
        TelaPrincipal tela_principal = new TelaPrincipal();
        tela_principal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_voltarActionPerformed

    private void tblGenerosSistemaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGenerosSistemaMouseClicked
        // TODO add your handling code here:
        linhaAtual  = tblGenerosSistema.getSelectedRow();
        valorSelecionado = String.valueOf(tblGenerosSistema.getValueAt(linhaAtual, 0));
    }//GEN-LAST:event_tblGenerosSistemaMouseClicked

    private void btnNovoGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoGeneroActionPerformed
        cadastrarGenero();
        doBancoParaTabela();
       
    }//GEN-LAST:event_btnNovoGeneroActionPerformed

    private void btnCadastrarGeneroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarGeneroUsuarioActionPerformed
        cadastraGeneroParaOUsuario();
        doBancoParaTabelaUsuario();
    }//GEN-LAST:event_btnCadastrarGeneroUsuarioActionPerformed

    private void btnExcluirGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirGeneroActionPerformed
        excluirDeMeusGeneros();
        doBancoParaTabelaUsuario();
    }//GEN-LAST:event_btnExcluirGeneroActionPerformed

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
            java.util.logging.Logger.getLogger(TelaGenerosPreferidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGenerosPreferidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGenerosPreferidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGenerosPreferidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGenerosPreferidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarGeneroUsuario;
    private javax.swing.JButton btnExcluirGenero;
    private javax.swing.JButton btnNovoGenero;
    private javax.swing.JButton btn_voltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable tblGenerosSistema;
    private javax.swing.JTable tblGenerosUsuario;
    private javax.swing.JTextField txtGeneroNovo;
    private javax.swing.JTextField txtNovoGenero;
    // End of variables declaration//GEN-END:variables

}
