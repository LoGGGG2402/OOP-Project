package UI.Entity;

import Entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class EntityDisplay extends JPanel {
    Entity entity;

    public EntityDisplay(Entity entity) {
        this.entity = entity;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Creating a label to display the name
        JLabel nameLabel = new JLabel(entity.getName());
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(nameLabel, BorderLayout.NORTH);

        // Creating a text area to display the description
        JTextArea descriptionArea = new JTextArea(entity.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        add(descriptionScrollPane, BorderLayout.CENTER);

        // Creating a panel to display the properties
        JPanel propertiesPanel = new JPanel(new GridLayout(0, 2));
        for (Map.Entry<String, String> entry : entity.getProperties().entrySet()) {
            JLabel jLabel = new JLabel(entry.getKey() + ":");
            jLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align left
            propertiesPanel.add(jLabel);

            JLabel valueLabel = new JLabel(entry.getValue());
            propertiesPanel.add(valueLabel);
        }
        add(propertiesPanel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {

        String name = "Book";
        String description = "A written or printed work consisting of pages glued or sewn together along one side and bound in covers.";

        Entity entity = new Entity(name, description);
        entity.addProperty("Author", "John Doe");
        entity.addProperty("Publisher", "Acme Publishing");
        entity.addProperty("Year", "2019");
        entity.addProperty("ISBN", "978-3-16-148410-0");


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Entity Display Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            EntityDisplay entityDisplay = new EntityDisplay(entity);
            frame.getContentPane().add(entityDisplay);

            frame.pack();
            frame.setVisible(true);
        });
    }
}
