
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author sd213335m
 */
public class MainWindow extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        methane3 = new javax.swing.JTextField();
        turnOnMotor = new javax.swing.JButton();
        turnOffMotor = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pumpFlow = new javax.swing.JTextField();
        pumpState = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        waterLevel = new javax.swing.JTextField();
        pumpFlowChoice = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        waterTankFlowChoice = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        alarms = new javax.swing.JTextArea();
        DefaultCaret caret = (DefaultCaret)alarms.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        methane = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        carbon = new javax.swing.JTextField();
        airFlow = new javax.swing.JTextField();
        lowWater = new javax.swing.JTextField();
        highWater = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lowWaterLabel = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        turnOnMotor.setText("Start pump");
        turnOnMotor.setActionCommand("turnOnPump");
        turnOnMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOnMotorActionPerformed(evt);
            }
        });

        turnOffMotor.setText("Stop pump");
        turnOffMotor.setActionCommand("turnOffPump");
        turnOffMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOffMotorActionPerformed(evt);
            }
        });

        jLabel1.setText("Current pump flow");

        jLabel2.setText("Water level");

        pumpFlow.setEditable(false);

        pumpState.setEditable(false);

        jLabel3.setText("Alarms");

        jLabel4.setText("Pump state");

        waterLevel.setEditable(false);

        pumpFlowChoice.setModel(new javax.swing.SpinnerNumberModel(20.0d, 5.0d, 40.0d, 0.5d));

        jLabel5.setText("Set pump flow");

        jLabel6.setText("Set water tank flow");

        waterTankFlowChoice.setModel(new javax.swing.SpinnerNumberModel(5.0d, 5.0d, 40.0d, 0.5d));

        alarms.setEditable(false);
        alarms.setColumns(20);
        alarms.setRows(5);
        alarms.setAutoscrolls(false);
        jScrollPane2.setViewportView(alarms);

        methane.setEditable(false);

        jLabel7.setText("CH4");

        carbon.setEditable(false);

        airFlow.setEditable(false);

        lowWater.setEditable(false);

        highWater.setEditable(false);

        jLabel8.setText("CO");

        jLabel9.setText("AirFlow");

        jLabel10.setText("HighWater");

        lowWaterLabel.setText("LowWater");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(turnOnMotor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(turnOffMotor))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(waterLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(pumpFlow, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pumpFlowChoice, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(waterTankFlowChoice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel9))))
                                    .addComponent(lowWaterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pumpState, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(airFlow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lowWater, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(methane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(highWater, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(carbon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turnOnMotor)
                    .addComponent(pumpState, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(turnOffMotor))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(methane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(carbon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(airFlow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(waterTankFlowChoice))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pumpFlowChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(highWater, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lowWater, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lowWaterLabel))
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pumpFlow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(waterLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void turnOnMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOnMotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_turnOnMotorActionPerformed

    private void turnOffMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffMotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_turnOffMotorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
                Thread mainWindowThread = new Thread(mainWindow);
                mainWindowThread.start();
            }
        });
    }

    private void loopSocket(int ipPort) {
        ServerSocket socketServer = null;
        try {
            // open Socket
            System.out.println("listening on port " + ipPort);
            socketServer = new ServerSocket(ipPort);

            while (true) {
                // open and close socket endlessly
                try {
                    // wait blocking for client to connect
                    Socket socket = socketServer.accept();
                    new Thread(() -> oneClient(socket, ipPort)).start();
                } catch (IOException e) {
                    //System.err.println(e.toString());
                    //System.exit(1);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        } finally {
            try {
                if (socketServer != null) {
                    socketServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void oneClient(Socket socket, int port) {
        try {
            socket.setKeepAlive(true);
//                    statusLine.setText("accepting commands at port " + ipPort);

// prepare input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();

// add listener for outgoing command
            if (port == 4021) {
                TurnOnOfMotor buttonListener = new TurnOnOfMotor(out);
                turnOnMotor.addActionListener(buttonListener);
                turnOffMotor.addActionListener(buttonListener);
                turnOnMotor.setEnabled(true);
                turnOffMotor.setEnabled(true);
                pumpFlowChoice.addChangeListener(new ChangePumpWaterTankFlow(out));
            }

            if (port == 4022) {
                waterTankFlowChoice.addChangeListener(new ChangeWaterTankFlow(out));
            }

// read blocking until socket is disconnected
            String cmd;
            System.out.println("Povezan");
            while ((cmd = in.readLine()) != null) {
//                System.out.println("Received Command:" + cmd);

                if (cmd.equals("disconnect")) {
                    break;
                }

                dispatchCommand(cmd);
            }
//                    statusLine.setText("listening");

// deactivate button
//                    requestButton.removeActionListener(buttonListener);
//                    requestButton.setEnabled(false);
//                    resetLights();
// clean up socket
            socket.close();
        } catch (Exception exception) {

        }
    }

    private synchronized void dispatchCommand(String cmd) {
        // check carLights
        if (cmd.startsWith("setWaterLevel=")) {
            java.awt.EventQueue.invokeLater(() -> {
                waterLevel.setText(cmd.split("=")[1]);
            });
        }
        if (cmd.startsWith("setPumpState=off")) {
            pumpState.setBackground(Color.RED);
        }
        if (cmd.startsWith("setPumpState=on")) {
            pumpState.setBackground(Color.GREEN);
        }
        if (cmd.startsWith("setPumpFlow=")) {
            pumpFlow.setText(cmd.split("=")[1]);
        }
        if (cmd.startsWith("setAlarm=")) {
            alarms.append(cmd.split("=")[1] + "\n");
        }
        if (cmd.startsWith("setMethaneLevel=on")) {
            methane.setBackground(Color.RED);
        }
        if (cmd.startsWith("setCarbonLevel=on")) {
            carbon.setBackground(Color.RED);
        }
        if (cmd.startsWith("setAirFlowLevel=on")) {
            airFlow.setBackground(Color.RED);
        }
        if (cmd.startsWith("setMethaneLevel=off")) {
            methane.setBackground(Color.green);
        }
        if (cmd.startsWith("setCarbonLevel=off")) {
            carbon.setBackground(Color.GREEN);
        }
        if (cmd.startsWith("setAirFlowLevel=off")) {
            airFlow.setBackground(Color.GREEN);
        }
        if (cmd.startsWith("setHighWaterLevel=on")) {
            highWater.setBackground(Color.RED);
        }
        if (cmd.startsWith("setLowWaterLevel=on")) {
            lowWater.setBackground(Color.RED);
        }
        if (cmd.startsWith("setHighWaterLevel=off")) {
            highWater.setBackground(Color.GREEN);
        }
        if (cmd.startsWith("setLowWaterLevel=off")) {
            lowWater.setBackground(Color.GREEN);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField airFlow;
    private javax.swing.JTextArea alarms;
    private javax.swing.JTextField carbon;
    private javax.swing.JTextField highWater;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField lowWater;
    private javax.swing.JLabel lowWaterLabel;
    private javax.swing.JTextField methane;
    private javax.swing.JTextField methane3;
    private javax.swing.JTextField pumpFlow;
    private javax.swing.JSpinner pumpFlowChoice;
    private javax.swing.JTextField pumpState;
    private javax.swing.JButton turnOffMotor;
    private javax.swing.JButton turnOnMotor;
    private javax.swing.JTextField waterLevel;
    private javax.swing.JSpinner waterTankFlowChoice;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        new Thread(() -> loopSocket(4020)).start();
        new Thread(() -> loopSocket(4021)).start();
        new Thread(() -> loopSocket(4022)).start();
//        loopSocket(4020);
    }

    public class TurnOnOfMotor
            implements ActionListener {

        private OutputStream out;

        public TurnOnOfMotor(OutputStream out) {
            this.out = out;
        }

        public void actionPerformed(ActionEvent event) {
            String s = event.getActionCommand();
            System.out.println(s);

            if (s.equals("turnOffPump") || s.equals("turnOnPump")) {
                try {
                    System.out.println(s);
                    out.write(s.getBytes());
                    out.flush();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }

    private class ChangeWaterTankFlow
            implements ChangeListener {

        private OutputStream out;

        public ChangeWaterTankFlow(OutputStream out) {
            this.out = out;
        }

        @Override
        public void stateChanged(ChangeEvent event) {
            try {
                System.out.println(waterTankFlowChoice.getValue());
                String cmd = "setWaterTenkFlow=" + waterTankFlowChoice.getValue();
                out.write(cmd.getBytes());
                out.flush();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    private class ChangePumpWaterTankFlow
            implements ChangeListener {

        private OutputStream out;

        public ChangePumpWaterTankFlow(OutputStream out) {
            this.out = out;
        }

        @Override
        public void stateChanged(ChangeEvent event) {
            try {
                System.out.println(pumpFlowChoice.getValue());
                String cmd = "setPumpFlow=" + pumpFlowChoice.getValue();
                out.write(cmd.getBytes());
                out.flush();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

}
