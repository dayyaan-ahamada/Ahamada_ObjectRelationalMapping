package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.ControllerCatalogue;

public class FenetreAccueil extends JFrame implements ActionListener {

    private JButton btAjouter, btSupprimer, btSelectionner;
    private JTextField txtAjouter;
    private JLabel lbNbCatalogues;
    private JComboBox cmbSupprimer, cmbSelectionner;
    private TextArea taDetailCatalogues;

    public FenetreAccueil() {
        setTitle("Catalogues");
        setBounds(500, 500, 200, 125);
        Container contentPane = getContentPane();
        JPanel panInfosCatalogues = new JPanel();
        JPanel panNbCatalogues = new JPanel();
        JPanel panDetailCatalogues = new JPanel();
        JPanel panGestionCatalogues = new JPanel();
        JPanel panAjouter = new JPanel();
        JPanel panSupprimer = new JPanel();
        JPanel panSelectionner = new JPanel();
        panNbCatalogues.setBackground(Color.white);
        panDetailCatalogues.setBackground(Color.white);
        panAjouter.setBackground(Color.gray);
        panSupprimer.setBackground(Color.lightGray);
        panSelectionner.setBackground(Color.gray);

        panNbCatalogues.add(new JLabel("Nous avons actuellement : "));
        lbNbCatalogues = new JLabel();
        panNbCatalogues.add(lbNbCatalogues);

        taDetailCatalogues = new TextArea();
        taDetailCatalogues.setEditable(false);
        new JScrollPane(taDetailCatalogues);
        taDetailCatalogues.setPreferredSize(new Dimension(300, 100));
        panDetailCatalogues.add(taDetailCatalogues);

        panAjouter.add(new JLabel("Ajouter un catalogue : "));
        txtAjouter = new JTextField(10);
        panAjouter.add(txtAjouter);
        btAjouter = new JButton("Ajouter");
        panAjouter.add(btAjouter);

        panSupprimer.add(new JLabel("Supprimer un catalogue : "));
        cmbSupprimer = new JComboBox();
        cmbSupprimer.setPreferredSize(new Dimension(100, 20));
        panSupprimer.add(cmbSupprimer);
        btSupprimer = new JButton("Supprimer");
        panSupprimer.add(btSupprimer);

        panSelectionner.add(new JLabel("Selectionner un catalogue : "));
        cmbSelectionner = new JComboBox();
        cmbSelectionner.setPreferredSize(new Dimension(100, 20));
        panSelectionner.add(cmbSelectionner);
        btSelectionner = new JButton("Selectionner");
        panSelectionner.add(btSelectionner);

        panGestionCatalogues.setLayout(new BorderLayout());
        panGestionCatalogues.add(panAjouter, "North");
        panGestionCatalogues.add(panSupprimer);
        panGestionCatalogues.add(panSelectionner, "South");

        panInfosCatalogues.setLayout(new BorderLayout());
        panInfosCatalogues.add(panNbCatalogues, "North");
        panInfosCatalogues.add(panDetailCatalogues, "South");

        contentPane.add(panInfosCatalogues, "North");
        contentPane.add(panGestionCatalogues, "South");
        pack();

        btAjouter.addActionListener(this);
        btSupprimer.addActionListener(this);
        btSelectionner.addActionListener(this);

        afficherCatalogues();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAjouter) {
            String texteAjout = txtAjouter.getText();
            if (!texteAjout.equals(""))
                if(!ControllerCatalogue.createCatalogue(texteAjout))
                    System.out.println("Une erreur s'est produite");
                afficherCatalogues();
                txtAjouter.setText(null);
        }
        if (e.getSource() == btSupprimer) {
            String texteSupprime = (String) cmbSupprimer.getSelectedItem();
            if (texteSupprime != null) {
                if(!ControllerCatalogue.supprimerCatalogue(texteSupprime))
                    System.out.println("Une erreur s'est produite");
                afficherCatalogues();
            }
        }
        if (e.getSource() == btSelectionner) {
            String texteSelection = (String) cmbSelectionner.getSelectedItem();
            if (texteSelection != null) {
                ControllerCatalogue.selectionnerCatalogue(texteSelection);
                new FenetrePrincipale();
                this.dispose();
            }
        }
    }

    private void afficherCatalogues() {
        modifierListesCatalogues(ControllerCatalogue.afficherCatalogues());
        modifierDetailCatalogues(ControllerCatalogue.afficherCataloguesEtNbProduits());
        modifierNbCatalogues(ControllerCatalogue.getNbCatalogues());
    }

    private void modifierListesCatalogues(String[] nomsCatalogues) {
        cmbSupprimer.removeAllItems();
        cmbSelectionner.removeAllItems();
        if (nomsCatalogues != null)
            for (String nomsCatalogue : nomsCatalogues) {
                cmbSupprimer.addItem(nomsCatalogue);
                cmbSelectionner.addItem(nomsCatalogue);
            }
    }

    private void modifierNbCatalogues(int nb) {
        lbNbCatalogues.setText(nb + " catalogues");
    }

    private void modifierDetailCatalogues(String[] detailCatalogues) {
        taDetailCatalogues.setText("");
        if (detailCatalogues != null)
            for (String detailCatalogue : detailCatalogues)
                taDetailCatalogues.append(detailCatalogue + "\n");
    }
}
