package com.example.top;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;

public class TopSwing extends JFrame {

    public static void run() {
        var frame = new JFrame("Order management system");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        var jfxPanel = new JFXPanel();
        frame.add(jfxPanel);

        Platform.runLater(() -> {
            var webView = new WebView();
            webView.getEngine().load("http://localhost:8080");
            Scene scene = new Scene(webView);
            jfxPanel.setScene(scene);
        });

        frame.setVisible(true);
    }

}
