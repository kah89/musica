/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.telas;

import br.com.projeto.dal.ModuloConexao;

import java.sql.*;
import br.com.projeto.dal.ModuloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class TelaPrincipal extends javax.swing.JFrame {
   
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
     /* Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_voltar = new javax.swing.JButton();
        btn_generosPreferidos = new javax.swing.JButton();
        btn_AvaliarMusicas = new javax.swing.JButton();
        btn_recomendacoes = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbNomeUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Projeto - Tela Principal");
        setResizable(false);

        btn_voltar.setText("Voltar");
        btn_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_voltarActionPerformed(evt);
            }
        });

        btn_generosPreferidos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_generosPreferidos.setText("Meus Gêneros Preferidos");
        btn_generosPreferidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generosPreferidosActionPerformed(evt);
            }
        });

        btn_AvaliarMusicas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_AvaliarMusicas.setText("Avaliar Músicas");
        btn_AvaliarMusicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AvaliarMusicasActionPerformed(evt);
            }
        });

        btn_recomendacoes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_recomendacoes.setText("Quero Recomendações");
        btn_recomendacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_recomendacoesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Usuário :");

        lbNomeUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbNomeUsuario.setForeground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btn_voltar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(27, 27, 27)
                                .addComponent(lbNomeUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btn_generosPreferidos, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(btn_AvaliarMusicas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_recomendacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btn_voltar)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btn_generosPreferidos, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btn_AvaliarMusicas, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btn_recomendacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        setSize(new java.awt.Dimension(504, 470));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_voltarActionPerformed
        try {
            String query = "truncate table usuarioAtual";
            pst = conexao.prepareStatement(query);
            pst.executeUpdate();
            login novoLogin = new login();
            novoLogin.setVisible(true);
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao voltar para tela de login.");
        }
        
    }//GEN-LAST:event_btn_voltarActionPerformed

    private void btn_generosPreferidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generosPreferidosActionPerformed
        // TODO add your handling code here:
        TelaGenerosPreferidos generos_preferidos = new TelaGenerosPreferidos();
        generos_preferidos.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_generosPreferidosActionPerformed

    private void btn_AvaliarMusicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AvaliarMusicasActionPerformed
        // TODO add your handling code here:
        TelaAvaliarMusicas avaliar_musicas = new TelaAvaliarMusicas();
        avaliar_musicas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_AvaliarMusicasActionPerformed

    private void btn_recomendacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_recomendacoesActionPerformed
        // TODO add your handling code here:
        TelaRecomendacoes recomendacoes = new TelaRecomendacoes();
        recomendacoes.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_recomendacoesActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AvaliarMusicas;
    private javax.swing.JButton btn_generosPreferidos;
    private javax.swing.JButton btn_recomendacoes;
    private javax.swing.JButton btn_voltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbNomeUsuario;
    // End of variables declaration//GEN-END:variables
}
