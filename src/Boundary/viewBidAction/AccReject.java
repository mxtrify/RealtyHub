package Boundary.viewBidAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccReject extends JPanel{

    private ActionButton acc;
    private ActionButton reject;

    public AccReject() {
        setVisible(true);
        setSize(10, 80);
        setLayout(new GridLayout(1, 2));

        ImageIcon cm = resizeIcon(new ImageIcon("src\\Boundary\\iconButtons\\checkmark.png"), 10, 10);
        acc = new ActionButton(cm);
        acc.setHorizontalAlignment(SwingConstants.CENTER);
        add(acc);

        ImageIcon cr = resizeIcon(new ImageIcon("src\\Boundary\\iconButtons\\cross.png"), 10, 10);
        reject = new ActionButton(cr);
        reject.setHorizontalAlignment(SwingConstants.CENTER);
        add(reject);

    }

    public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

    public void initEvent(TableActionEvent event, int row){
        acc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.onAccept(row);
            }
        });
        reject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.onReject(row);
            }
        });
    }
}
