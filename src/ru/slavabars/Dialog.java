package ru.slavabars;

import javax.swing.*;

public class Dialog {
    public Dialog(JFrame frame, String name, String text){
        JDialog jDialog = new JDialog(frame, name);
        JLabel jLabel = new JLabel(text);
        jDialog.add(jLabel);
        jDialog.setSize(350,50);
        jDialog.setVisible(true);
    }
}
