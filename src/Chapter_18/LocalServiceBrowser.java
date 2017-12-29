package Chapter_18;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LocalServiceBrowser {

    JPanel mainPanel;
    JComboBox serviceList;
    ServiceServer server;

    public void buildGUI() {
        JFrame frame = new JFrame("Local Browser");

        mainPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        Object[] services = getServicesList();
        serviceList = new JComboBox(services);
        frame.getContentPane().add(BorderLayout.NORTH, serviceList);

        serviceList.addActionListener(new MyListListener());
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    void loadService(Object serviceSelection) {
        try {
            ServiceServerImpl srv = new ServiceServerImpl();
            Service svc = srv.getService(serviceSelection);
            mainPanel.removeAll();
            mainPanel.add(svc.getGuiPanel());
            mainPanel.validate();
            mainPanel.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Object[] getServicesList() {
        Object[] services = null;
        try {
            ServiceServerImpl srv = new ServiceServerImpl();
            services = srv.getServiceList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return services;
    }

    class MyListListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            Object selection = serviceList.getSelectedItem();
            loadService(selection);
        }
    }
}
