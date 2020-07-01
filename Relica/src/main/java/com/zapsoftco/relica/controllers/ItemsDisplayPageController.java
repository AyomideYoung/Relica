package com.zapsoftco.relica.controllers;

import com.zapsoftco.relica.util.ResourceManager;
import com.zapsoftco.relica.util.reusable.LabelComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class ItemsDisplayPageController{

    @FXML
    private LabelComboBox categoryBox;

    @FXML
    private ComboBox sortBox;

    @FXML
    private TextField searchField;

    @FXML
    private FlowPane itemsListContainer;

    private Logger logger = LoggerFactory.getLogger(ItemsDisplayPageController.class);


    public void refreshItemsListWithNewConstraints(){

    }

    public void searchForItemUsingConstraints(){

    }
}
