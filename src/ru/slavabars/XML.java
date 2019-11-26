package ru.slavabars;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class XML {

    public XML(String langFrom, String textFrom, String langTo, String textTo) throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("translate");
        document.appendChild(root);

        Element rawText = document.createElement("rawText");
        root.appendChild(rawText);
        Attr rawTextAttrCode = document.createAttribute("code");
        rawTextAttrCode.setValue(langFrom);
        rawText.setAttributeNode(rawTextAttrCode);
        Attr rawTextAttrValue = document.createAttribute("value");
        rawTextAttrValue.setValue(textFrom);
        rawText.setAttributeNode(rawTextAttrValue);

        Element translateText = document.createElement("translateText");
        root.appendChild(translateText);
        Attr translateTextAttrCode = document.createAttribute("code");
        translateTextAttrCode.setValue(langTo);
        translateText.setAttributeNode(translateTextAttrCode);
        Attr translateTextAttrValue = document.createAttribute("value");
        translateTextAttrValue.setValue(textTo);
        translateText.setAttributeNode(translateTextAttrValue);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        Element time = document.createElement("time");
        root.appendChild(time);
        Attr timeAttrValue = document.createAttribute("value");
        timeAttrValue.setValue(strDate);
        time.setAttributeNode(timeAttrValue);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StringWriter text = new StringWriter();
        StreamResult streamResult = new StreamResult(text);

        transformer.transform(domSource, streamResult);

        JFileChooser fc = new JFileChooser();
        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
            try ( FileWriter fw = new FileWriter(fc.getSelectedFile()) ) {
                fw.write(String.valueOf(text));
            }
            catch ( IOException e ) {
                System.out.println("Не сохранен");
            }
        }
    }
}


//<translate>
//<rawText code=”ru” value=”Кошка” />
//<translateText code=”en” value=”Cat”/>
//<time value=”03.04.2018 T11:30”/>
//</translate>
