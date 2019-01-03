package edu.uh.tech.cis3368.finalexam;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    @FXML
    private TextField mouseName;

    @FXML
    private ListView catMice;

    @FXML
    private ListView mouseList;

    @FXML
    private ImageView catImage;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private MouseRepository mouseRepository;

    private static final DataFormat INTEREST_LIST = new DataFormat("cis3368/researchInterest");
    private Cat cat;

    public void onDragDetected(MouseEvent mouseEvent) {

        int selected = mouseList.getSelectionModel().getSelectedIndices().size();
        System.out.println(String.format("%d selected",selected));
        if(selected > 0){
            Dragboard dragboard = mouseList.startDragAndDrop(TransferMode.MOVE);
            ArrayList<Mouse> selectedItems = new ArrayList<>(mouseList.getSelectionModel().getSelectedItems());
            ClipboardContent content = new ClipboardContent();
            content.put(INTEREST_LIST,selectedItems);
            dragboard.setContent(content);
            mouseEvent.consume();
        } else {
            System.out.println("nothing selected");
            mouseEvent.consume();
        }


    }

    public void onDragDone(DragEvent dragEvent) {
        System.out.println("Drag done detected");
        TransferMode tm = dragEvent.getAcceptedTransferMode();
        if(tm == TransferMode.MOVE) {
            removeSelectedItems();
        }
        dragEvent.consume();

        mouseRepository.findAll().forEach(System.out::println);
    }

    public void onDragDropped(DragEvent dragEvent) {
        boolean dragCompleted = false;
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasContent(INTEREST_LIST)) {
            ArrayList<Mouse> mice = (ArrayList<Mouse>) dragboard.getContent(INTEREST_LIST);
            mice.forEach(mouse -> {
                cat.addMouse(mouse);
            });
            catRepository.save(cat);
            catMice.getItems().addAll(mice);
            dragCompleted = true;
        }
        dragEvent.consume();
    }

    public void onDragOver(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if(dragboard.hasContent(INTEREST_LIST)){
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cat = new Cat();
        cat.setName("Fido");
        cat.setFavoriteToy("silver bell");
        cat.setBreed("tabby");
        catRepository.save(cat);

        Mouse mouse = new Mouse();
        mouse.setName("Mickey");
        mouseRepository.save(mouse);
        mouseList.getItems().add(mouse);

    }

    private void removeSelectedItems() {
        ObservableList selectedFleas = mouseList.getSelectionModel().getSelectedItems();
        mouseList.getItems().removeAll(selectedFleas);
        mouseList.getSelectionModel().clearSelection();
    }

    public void spitOutMice(ActionEvent actionEvent) {
        cat.dropMice();
        catMice.getItems().clear();
    }

    public void doAddMouse(ActionEvent actionEvent)
    {
        Mouse mouse = new Mouse();
        var newMouseName = mouseName.getText();
        if (!newMouseName.isEmpty()) {
            mouse.setName(mouseName.getText());
            mouseList.getItems().add(mouse);
        }
    }
}
