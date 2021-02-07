package il.ac.hit.costmanager.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageWindow
{
    private final JFrame messageWindowFrame;
    private final JPanel messageWindowPanel;
    private final JButton btOk;
    private final JLabel lbMessage;

    /**
     * Creates a window to display a message to the user.
     * @param messageWindowFrame The Jframe for the window.
     * @param message A string message to display in the window.
     */
    public MessageWindow(JFrame messageWindowFrame, String message)
    {
        this.messageWindowFrame = messageWindowFrame;
        lbMessage = new JLabel(message);
        messageWindowPanel = new JPanel();
        btOk = new JButton("OK");
    }

    /**
     * Returns a Jpanel filled with the desired window.
     * @return a Jpanel filled with the desired window.
     */
    public JPanel createWindow()
    {
        messageWindowPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        messageWindowPanel.add(lbMessage, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        messageWindowPanel.add(btOk, gbc);

        btOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageWindowFrame.setVisible(false);
                messageWindowFrame.dispose();
            }
        });

        return messageWindowPanel;
    }

}
