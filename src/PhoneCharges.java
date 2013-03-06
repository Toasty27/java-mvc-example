import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;

public class PhoneCharges
{
    private static JFrame       phoneChargesFrame;
    private static JPanel       ratePanel, durationPanel, calculatePanel;
    private static JRadioButton rateDaytimeRB, rateEveningRB, rateOffpeakRB;
    private static JLabel       durationLabel;
    private static JTextField   durationTextField;
    private static JButton      calculateButton;
    
    private static CalcButtonListener calculateButtonListener;
    
    public static final double DAYTIME_RATE = 0.25;
    public static final double EVENING_RATE = 0.12;
    public static final double OFFPEAK_RATE = 0.05;

    public PhoneCharges()
    {
        final int WINDOW_WIDTH = 280;
        final int WINDOW_HEIGHT = 235;

        // Create GUI components
        durationLabel = new JLabel("Minutes: ");
        durationTextField = new JTextField(10);
        
        calculateButton = new JButton("Calculate Charges");
        calculateButtonListener = new CalcButtonListener();
        calculateButton.addActionListener(calculateButtonListener);
        
        rateDaytimeRB = new JRadioButton("Daytime - 8:00 a.m. to 5:00 p.m.", true);
        rateEveningRB = new JRadioButton("Evening - 5:00 p.m. to 11:00 p.m.", false);
        rateOffpeakRB = new JRadioButton("Off-Peak - 11:00 p.m. to 8:00 a.m.", false);
        
            // Put radio buttons in same group
            ButtonGroup group = new ButtonGroup();
            group.add(rateDaytimeRB);
            group.add(rateEveningRB);
            group.add(rateOffpeakRB);
            
            // Add listeners for radio buttons
            rateDaytimeRB.addActionListener(new RadioButtonListener());
            rateEveningRB.addActionListener(new RadioButtonListener());
            rateOffpeakRB.addActionListener(new RadioButtonListener());
            
            //  Trigger rate change in CalcButtonListener class so DAYTIME_RATE
            //+ is used as the default rate
            rateDaytimeRB.doClick();

        // Create panels and add components
        ratePanel = new JPanel();
        ratePanel.setLayout(new GridLayout(3,1));
        ratePanel.setBorder(BorderFactory.createTitledBorder("Select a Rate"));
        ratePanel.add(rateDaytimeRB);
        ratePanel.add(rateEveningRB);
        ratePanel.add(rateOffpeakRB);
        
        durationPanel = new JPanel();
        durationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        durationPanel.setBorder(BorderFactory.createTitledBorder("Duration of Call"));
        durationPanel.add(durationLabel);
        durationPanel.add(durationTextField);
        
        calculatePanel = new JPanel();
        durationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        calculatePanel.add(calculateButton);

        // Create a frame and add the panels
        phoneChargesFrame = new JFrame("Phone Charges");
        phoneChargesFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        phoneChargesFrame.add(ratePanel);
        phoneChargesFrame.add(durationPanel);
        phoneChargesFrame.add(calculatePanel);
        
        // Display the frame on the screen
        phoneChargesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        phoneChargesFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        phoneChargesFrame.setLocationRelativeTo(null); // center on screen
        phoneChargesFrame.setVisible(true);
    }

    private class RadioButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == rateDaytimeRB)
            {
                calculateButtonListener.setRate(DAYTIME_RATE);
            }
            else if (e.getSource() == rateEveningRB)
            {
                calculateButtonListener.setRate(EVENING_RATE);
            }
            else if (e.getSource() == rateOffpeakRB)
            {
                calculateButtonListener.setRate(OFFPEAK_RATE);
            }
        }
    }
    
    private class CalcButtonListener implements ActionListener
    {
        private double rate;
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            double charges;
            int duration;
            String input;

            try // If input isn't a number, display an error message
            {
                input = durationTextField.getText();
                duration = Integer.parseInt(input);
                charges = duration * rate;
                displayCharges(duration, rate, charges);
            }
            catch (NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(
                        null,
                        "Minutes not valid",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE
                        );
            }
        }
        
        public void displayCharges(int duration, double rate, double charges)
        {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            
            JOptionPane.showMessageDialog(
                    null,
                    duration + " minutes at the rate of $" + rate + " per minute. \n" +
                    "Total Charges are $" + formatter.format(charges),
                    "Phone Charges",
                    JOptionPane.INFORMATION_MESSAGE
                    );
        }
        
        public void setRate(double r)
        {
            rate = r;
        }
    }

    public static void main(String[] args)
    {
        new PhoneCharges();
    }
}
