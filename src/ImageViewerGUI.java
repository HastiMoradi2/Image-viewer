import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.awt.image.RescaleOp;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ImageViewerGUI extends JFrame implements ActionListener{
    JPanel mainPanel = new JPanel();
    JButton selectFileButton = new JButton("Select image");
    JButton showImageButton = new JButton("Show Image");
    JButton resizeButton = new JButton("Resize Button");;
    JButton grayscaleButton = new JButton("Gray Scale");;
    JButton brightnessButton = new JButton("Brightness");
    JButton closeButton = new JButton("Exit");;
    JButton showResizeButton = new JButton("Resize");;
    JButton showBrightnessButton = new JButton("Result");;
    JButton backButton = new JButton("Back");
    JTextField widthTextField = new JTextField();
    JTextField heightTextField = new JTextField();
    JTextField brightnessTextField = new JTextField();

    String filePath = "/Users/hasti/Desktop/images";
    File file ;

    JFileChooser fileChooser = new JFileChooser(filePath);

    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setResizable(true);
        mainPanel();

        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif"));

        this.setVisible(true);

        addActionevent();
    }
    public void mainPanel(){
        // Create main panel for adding to Frame
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(210, 219, 237));

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(210, 219, 237));
        buttonsPanel.setBounds(200,100,300,100);
        buttonsPanel.setLayout(new GridLayout(3, 2));

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel(){
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(new BorderLayout());
        resizePanel.setBorder(new EmptyBorder(20,20,20,20));
        resizePanel.setBackground(new Color(210, 219, 237));

        JPanel backAndShowPanel = new JPanel();
        backAndShowPanel.setBackground(new Color(210, 219, 237));
        resizePanel.add(backAndShowPanel, BorderLayout.SOUTH);
        backAndShowPanel.setLayout(new GridLayout(1,2,100,100));
        backAndShowPanel.setBorder(new EmptyBorder(20,140,20,140));
        backAndShowPanel.add(backButton);
        backAndShowPanel.add(showResizeButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(210, 219, 237));
        resizePanel.add(infoPanel, BorderLayout.CENTER);
        infoPanel.setBorder(new EmptyBorder(10,120,10,260));
        infoPanel.setLayout(new GridLayout(3,2));
        JLabel widthLabel = new JLabel("Width");
        JLabel hightLabel = new JLabel("Hight");
        JLabel emptyLabel = new JLabel("");//for the buttons to align perfectly
        JLabel resizeSectionLabel = new JLabel("      Resize Section");//the spaces are again, for buttons to align
        infoPanel.add(emptyLabel);
        infoPanel.add(resizeSectionLabel);
        infoPanel.add(widthLabel);
        infoPanel.add(widthTextField);
        infoPanel.add(hightLabel);
        infoPanel.add(heightTextField);

        this.remove(mainPanel);
        this.add(resizePanel);
        this.revalidate();
        this.repaint();

    }
    public void brightnessPanel(){
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setBackground(new Color(210, 219, 237));
        brightnessPanel.setLayout(null);
        JLabel brightnessLabel = new JLabel("<HTML>Enter f:<BR>(must be between 0 and 1)</HTML>");
        brightnessLabel.setBounds(150,100,180,60);
        brightnessTextField.setBounds(370,110,180,40);
        backButton.setBounds(100,200,100,40);
        showBrightnessButton.setBounds(500,200,100,40);
        brightnessPanel.add(brightnessTextField);
        brightnessPanel.add(backButton);
        brightnessPanel.add(brightnessLabel);
        brightnessPanel.add(showBrightnessButton);

        this.remove(mainPanel);
        this.add(brightnessPanel);
        this.revalidate();
        this.repaint();
    }

    public void chooseFileImage(){
        fileChooser.showOpenDialog(ImageViewerGUI.this);

    }
    public void showOriginalImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JPanel imagePanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.setBorder(new EmptyBorder(80,20,20,20));

        JLabel pictureLabel = new JLabel();
        imagePanel.add(pictureLabel);
        tempPanel.add(imagePanel, BorderLayout.CENTER);
        file = fileChooser.getSelectedFile();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(String.valueOf(file)).getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT));
        pictureLabel.setIcon(imageIcon);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void grayScaleImage() throws IOException {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JPanel imagePanel = new JPanel();

        tempPanel.setLayout(new BorderLayout());
        tempPanel.setBorder(new EmptyBorder(80,20,20,20));

        JLabel pictureLabel = new JLabel();
        imagePanel.add(pictureLabel);
        tempPanel.add(imagePanel, BorderLayout.CENTER);
        file = fileChooser.getSelectedFile();
        BufferedImage bufferedImage = ImageIO.read(file);
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs,null);
        BufferedImage image = op.filter(bufferedImage,null);
        ImageIcon imageIcon = new ImageIcon(image);
        pictureLabel.setIcon(imageIcon);

        tempPanel.add(imagePanel);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showResizeImage(int w, int h){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JPanel imagePanel = new JPanel();

        tempPanel.setLayout(new BorderLayout());
        tempPanel.setBorder(new EmptyBorder(80,20,20,20));

        JLabel pictureLabel = new JLabel();
        imagePanel.add(pictureLabel);
        tempPanel.add(imagePanel, BorderLayout.CENTER);
        file = fileChooser.getSelectedFile();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(String.valueOf(file)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        pictureLabel.setIcon(imageIcon);

        tempPanel.add(imagePanel);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showBrightnessImage(float f) throws IOException {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JPanel imagePanel = new JPanel();

        tempPanel.setLayout(new BorderLayout());
        tempPanel.setBorder(new EmptyBorder(80,20,20,20));

        tempPanel.add(imagePanel, BorderLayout.CENTER);
        file = fileChooser.getSelectedFile();
        BufferedImage bufferedImage = ImageIO.read(file);
        int brightness = (int)(255*f);
        RescaleOp rescaleOp = new RescaleOp(f, 0, null);
        BufferedImage newImage = rescaleOp.filter(bufferedImage, null);

        JLabel pictureLabel = new JLabel(new ImageIcon(newImage));
        imagePanel.add(pictureLabel);

        tempPanel.add(imagePanel);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void noImageChosen (){
        JPanel noImagePanel = new JPanel();
        noImagePanel.setBackground(new Color(210, 219, 237));
        noImagePanel.setLayout(null);
        JLabel noImageLabel = new JLabel("Select and image first!");
        noImageLabel.setBounds(277,100,180,60);
        backButton.setBounds(300,200,100,40);
        noImagePanel.add(backButton);
        noImagePanel.add(noImageLabel);

        this.getContentPane().removeAll();
        this.add(noImagePanel);
        this.revalidate();
        this.repaint();
    }

    public void addActionevent(){
        resizeButton.addActionListener(this);
        showImageButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        showResizeButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        showBrightnessButton.addActionListener(this);
        selectFileButton.addActionListener(this);
        closeButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resizeButton) {
            if(fileChooser.getSelectedFile()==null)
                noImageChosen();
            else
                resizePanel();
        } else if (e.getSource() == selectFileButton) {
            chooseFileImage();
        } else if (e.getSource() == showImageButton) {
            if(fileChooser.getSelectedFile()==null)
                noImageChosen();
            else
                showOriginalImage();

        } else if (e.getSource() == grayscaleButton) {
            try {
                if(fileChooser.getSelectedFile()==null)
                    noImageChosen();
                else
                    grayScaleImage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == showResizeButton) {
            try {
                int h = parseInt(heightTextField.getText());
                int w = parseInt(widthTextField.getText());
                showResizeImage(h, w);
            }catch(Exception exception){
            System.out.println("textfields are invalid");}
        } else if (e.getSource() == brightnessButton) {
            if(fileChooser.getSelectedFile()==null)
                noImageChosen();
            else
                brightnessPanel();

        } else if (e.getSource() == showBrightnessButton) {
            try {
                    float f = parseFloat(brightnessTextField.getText());
                    if (f <= 1 && f >= 0)
                        showBrightnessImage(f);
                    else
                        System.out.println("the given f isn't compatible");

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }catch (Exception exception){
                System.out.println("textfiled is invalid");
            }

        } else if (e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource() == backButton) {
            this.getContentPane().removeAll();
            this.add(mainPanel);
            this.revalidate();
            this.repaint();
        }
    }
}