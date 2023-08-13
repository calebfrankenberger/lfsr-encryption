package com.calebfrankenberger.lfsr;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EventController {

    public File selectedFile;
    public String lfsrSeed;
    public int lfsrTap;
    public BufferedImage originalImage;
    public BufferedImage encryptedImage;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField seedField;

    @FXML
    private TextField tapField;

    @FXML
    private TextArea textArea;

    @FXML
    private Button chooseFileBtn;

    @FXML
    private Button saveImgBtn;

    @FXML
    private Label seedError;

    @FXML
    private Label tapError;

    @FXML
    private Label imgError;

    @FXML
    protected void onEncryptButton() throws IOException {
        if(validateInputs()) {
            imgError.setVisible(false);
            seedError.setVisible(false);
            tapError.setVisible(false);

            LFSR lfsr = new LFSR(lfsrSeed, lfsrTap);
            BufferedImage bufferedOriginalImage = ImageIO.read(selectedFile);
            BufferedImage bufferedEncryptedImage = (EncryptImage.encrypt(bufferedOriginalImage, lfsr, textArea));

            JFrame originalImageFrame = getImagePopup(bufferedOriginalImage, "Original Image");
            originalImageFrame.setVisible(true);
            JFrame encryptedImageFrame = getImagePopup(bufferedEncryptedImage, "Encrypted Image");
            encryptedImageFrame.setVisible(true);

            originalImage = bufferedOriginalImage;
            encryptedImage = bufferedEncryptedImage;

            saveImgBtn.setDisable(false);
        }
    }

    // Application will only accept PNG and JPG files
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg");

    @FXML
    protected void onFileChoose() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Select an image to encrypt");

        selectedFile = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        if(selectedFile != null) {
            chooseFileBtn.setDisable(true);
            chooseFileBtn.setText("Selected!");
        }
    }

    @FXML
    protected void onSaveImageClick() throws IOException {
        FileChooser fileSaver = new FileChooser();
        fileSaver.setTitle("Save");
        fileSaver.getExtensionFilters().add(extensionFilter);
        File targetFile = fileSaver.showSaveDialog(borderPane.getScene().getWindow());
        if(targetFile != null) {
            File fileOutput = new File(targetFile.getAbsolutePath());
            ImageIO.write(encryptedImage, "png", fileOutput);

            saveImgBtn.setDisable(true);
            chooseFileBtn.setDisable(false);
            imgError.setVisible(false);
        }
    }

    public boolean validateInputs() {
        // 0 = file 1 = seed 2 = tap
        boolean[] results = new boolean[3];

        // Check if the image is loaded
        if(selectedFile != null)
            results[0] = true;

        // Check if the seed can be loaded as either 0s and 1s or a string of alphanumeric characters
        if(seedField.getText() != null) {
            lfsrSeed = seedField.getText();
            // Count the number of characters that are either a 0 or a 1
            int count = 0;
            for(char c : lfsrSeed.toCharArray()) {
                if (String.valueOf(c).equals("0") || String.valueOf(c).equals("1"))
                    count++;
            }
            // If all the characters are 0s and 1s, use the seed as direct binary. Otherwise, try to parse it as alphanumeric
            if(count == lfsrSeed.length()) {
                results[1] = true;
            } else {
                try {
                    lfsrSeed = ByteConversionUtil.convertPlainTextToBits(lfsrSeed);
                    results[1] = true;
                } catch(Exception ignored) {
                    // Keep result as false during exceptions
                }
            }
        }

        // Check if the tap is a single integer and less than or equal to the length of the string
        try {
            int tap = Integer.parseInt(tapField.getText());
            if(tap <= lfsrSeed.length())
                results[2] = true;
        } catch(Exception ignored) {
            // Keep result as false during exceptions
        }

        imgError.setVisible(!results[0]);
        seedError.setVisible(!results[1]);
        // If the seed isn't compatible tap doesn't matter
        if(!seedError.isVisible())
            tapError.setVisible(!results[2]);


        for(boolean b : results)
            if(!b) return false;
        return true;
    }

    public JFrame getImagePopup(BufferedImage image, String title) {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();
        return frame;
    }

}