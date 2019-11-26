package ru.slavabars;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Random;

public class App {
    private JPanel panel;
    private JComboBox comboBox1, comboBox2;
    private JTextArea textFrom, textTo;
    private JButton btnTranslate, btnXml;
    static JFrame frame;

    public App() {
        DB langs = new DB();
        Map<Integer, String> map = (Map<Integer, String>) langs.langs();

        comboBox1.setModel(new DefaultComboBoxModel(map.values().toArray()));
        comboBox2.setModel(new DefaultComboBoxModel(map.values().toArray()));

        btnTranslate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if ((String) comboBox1.getSelectedItem() == (String) comboBox2.getSelectedItem()) {
                        comboBox2.setSelectedIndex(new Random().nextInt(50));
                        throw new Exception("Нельзя переводить на себя");
                    }

                    Yandex yandex = new Yandex();

                    String getLang = yandex.getLanguages((String) comboBox1.getSelectedItem());
                    YandexResponse response = new Gson().fromJson(getLang, YandexResponse.class);

                    if (!response.getLangs().containsKey((String) comboBox2.getSelectedItem())) {
                        throw new ArrayStoreException("Перевод запрещен");
                    }

                    String translated = yandex.translate((String) textFrom.getText(), (String) comboBox1.getSelectedItem(), (String) comboBox2.getSelectedItem());
                    if (translated != null) {
                        YandexResponse responseLang = new Gson().fromJson(translated, YandexResponse.class);
                        if (responseLang.getCode() == 200) {
                            textTo.setText(responseLang.getText().get(0));
                        } else {
                            new Dialog(frame, "Ошибка", "Проблема с переводом");
                        }
                    }

                } catch (ArrayStoreException e) {
                    new Dialog(frame, "Ошибка", e.getMessage());
                } catch (Exception e) {
                    new Dialog(frame, "Ошибка", e.getMessage());
                }
            }
        });
        btnXml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (textTo.getText().isEmpty()) {
                        throw new Exception("Сначала переведите текст");
                    }
                   new XML((String) comboBox1.getSelectedItem(),(String) textFrom.getText(),(String) comboBox2.getSelectedItem(),(String) textTo.getText());
                } catch (Exception e) {
                    new Dialog(frame, "Ошибка", e.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
