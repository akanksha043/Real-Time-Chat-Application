package com.mycompany.chatting_application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Chatting_Application extends JFrame implements ActionListener {
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Chatting_Application() {
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(0, 0, 0));
        p1.setBounds(0, 0, 450, 65);
        p1.setLayout(null);
        f.add(p1);

        // Load the image from an absolute path
        ImageIcon i1 = new ImageIcon("C:\\Users\\chaud\\Downloads\\NetBeansProjects\\Chatting_Application\\src\\resources\\icons\\4.png");
        Image i2 = i1.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        // Load the profile image using an absolute path
        ImageIcon i4 = new ImageIcon("C:\\Users\\chaud\\Downloads\\NetBeansProjects\\Chatting_Application\\src\\resources\\icons\\profile.jpeg");
        Image i5 = i4.getImage().getScaledInstance(48, 48, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 48, 48);
        p1.add(profile);

        // Load the video icon using an absolute path
        ImageIcon i7 = new ImageIcon("C:\\Users\\chaud\\Downloads\\NetBeansProjects\\Chatting_Application\\src\\resources\\icons\\video.png");
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        // Load the pp icon using an absolute path
        ImageIcon i10 = new ImageIcon("C:\\Users\\chaud\\Downloads\\NetBeansProjects\\Chatting_Application\\src\\resources\\icons\\pp.png");
        Image i11 = i10.getImage().getScaledInstance(52, 44, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel pp = new JLabel(i12);
        pp.setBounds(355, 20, 35, 30);
        p1.add(pp);

        // Load the morevert icon using an absolute path
        ImageIcon i13 = new ImageIcon("C:\\Users\\chaud\\Downloads\\NetBeansProjects\\Chatting_Application\\src\\resources\\icons\\3icon.png");
        Image i14 = i13.getImage().getScaledInstance(8, 23, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(410, 20, 10, 25);
        p1.add(morevert);

        JLabel name = new JLabel("USER1");
        name.setBounds(90, 20, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("ARIAL", Font.BOLD, 18));
        p1.add(name);
        
        JLabel status = new JLabel("Active");
        status.setBounds(90, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("ARIAL", Font.BOLD, 12));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(5, 70, 440, 570);
        f.add(a1);
        
        text = new JTextField();  // Use the class-level variable
        text.setBounds(5, 651, 310, 42);
        text.setFont(new Font("ARIAL", Font.PLAIN, 16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320, 651, 123, 42);
        send.setBackground(new Color(0, 0, 0));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("ARIAL", Font.PLAIN, 16));
        f.add(send);
        
        f.setUndecorated(true);
        f.setSize(450, 700);
        f.setVisible(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setLocation(200, 50);
    }

    public void actionPerformed(ActionEvent ae) {
        try{
         String out = text.getText();
         
         JPanel p2 = formatLabel(out);
         JLabel output = new JLabel(out);
         output.setForeground(Color.WHITE);
         a1.setLayout(new BorderLayout());

         JPanel right = new JPanel(new BorderLayout());
         right.add(p2, BorderLayout.LINE_END);
         vertical.add(right);
         vertical.add(Box.createVerticalStrut(15));

         a1.add(vertical, BorderLayout.PAGE_START);
         dout.writeUTF(out);
         text.setText("");
  
         f.repaint();
         f.invalidate();
         f.validate();
    }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px; color: white;\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(0, 0, 0));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
  
        panel.add(time);
        
        return panel;
    }

    public static void main(String[] args) {
        new Chatting_Application();
        try {
            ServerSocket skt = new ServerSocket(6001);
            while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
