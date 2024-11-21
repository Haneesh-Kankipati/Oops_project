import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Room extends JPanel {
    Canvas canvas;
    HotCorner lt;
    HotCorner rb;
    int borderwidth = 3;
    Color x;
    ArrayList<Furniture> furnitures;
    ArrayList<Door> doors;
    ArrayList<Window> windows;
    MouseAdapter mouse;
    MouseAdapter mouse1;
    MouseAdapter mouse2;
    String color;

    //int gridSize;
    // popup menu
    JPopupMenu popup = new JPopupMenu();
    JMenuItem rotate = new JMenuItem("Rotate");
    JMenuItem delete = new JMenuItem("Delete");
    JMenuItem addroom = new JMenuItem("Add room");
    JMenuItem addfurniture = new JMenuItem("Add furniture");
    JMenuItem adddoor = new JMenuItem("Add door");
    JMenuItem addwindow = new JMenuItem("Add window");
    


    public Room(java.awt.Color x,Canvas canvass){
        canvas = canvass;
        furnitures= new ArrayList<Furniture>();
        doors= new ArrayList<Door>();
        windows=new ArrayList<Window>();
        if(x==Color.RED){
            color="Red";
        }
        if(x==Color.BLUE){
            color="Blue";
        }
        if(x==Color.YELLOW){
            color="Yellow";
        }
        if(x==Color.GREEN){
            color="Green";
        }
        //Basic setup
        setLayout(null);
        setSize(100,50);
        setPreferredSize(new Dimension(100,50));
        setBackground(x);
        setVisible(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK,borderwidth,true));
        // initialising popup:
        popup.add(rotate);
        rotate.addActionListener(e->{
            this.rotate();
        });
        popup.add(delete);
        delete.addActionListener(e->{
            this.delete(this);
        });
        //popup.add(addroom);
        //DO CENTRE ALLIGNMENT
        /*addroom.addActionListener(e->{
            String[] roomtype={"Bedroom","Bathroom","Living room","Dining"};
            String[] direction={"North","South","East","West"};
            String[] orientation1={"Right","Centre","Left"};
            String[] orientation2={"Top","Centre","Bottom"};
            JComboBox<String> d1=new JComboBox<>(roomtype);
            JComboBox<String> d2=new JComboBox<>(direction);
            JComboBox<String> d3=new JComboBox<>(orientation1);
            JComboBox<String> d4=new JComboBox<>(orientation2);
            JPanel panel = new JPanel();
            panel.add(new JLabel("Select room type:"));
            panel.add(d1);
            panel.add(new JLabel("Select direction:"));
            panel.add(d2);
            int result = JOptionPane.showConfirmDialog(this, panel, "Select Options", 
                                                         JOptionPane.OK_CANCEL_OPTION, 
                                                         JOptionPane.PLAIN_MESSAGE);

            // Check if the user clicked OK
            if (result == JOptionPane.OK_OPTION) {
                String s1 = (String) d1.getSelectedItem();
                String s2 = (String) d2.getSelectedItem();
                if(s2=="North" || s2=="South"){
                    JPanel panel1 = new JPanel();
                    panel1.add(new JLabel("Select orientation:"));
                    panel1.add(d3);
                    panel1.add(new JLabel("Width:"));
                    JTextField t1 = new JTextField(5);
                    panel1.add(t1);
                    panel1.add(new JLabel("Height:"));
                    JTextField t2 = new JTextField(5);                    
                    panel1.add(t2);
                    int result1 = JOptionPane.showConfirmDialog(this, panel1, "Select Options", 
                                                         JOptionPane.OK_CANCEL_OPTION, 
                                                         JOptionPane.PLAIN_MESSAGE);
                    if (result1 == JOptionPane.OK_OPTION){
                        String s3=(String) d3.getSelectedItem();
                        int w1=Integer.parseInt(t1.getText());
                        int h1=Integer.parseInt(t2.getText());
                        switch (s2) {
                            case "North":
                            switch (s3) {
                                case "Left":
                                switch (s1) {
                                    case "Bedroom":                                       
                                        Room room=canvas.addRoom(getX(),getY()-h1,w1,h1,Color.RED);
                                        if(this.room_overlap(getX(),getY()-h1,getX()+w1,getY())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }                                                
                                        break;
                                    case "Bathroom":
                                        Room room1=canvas.addRoom(getX(),getY()-h1,w1,h1,Color.BLUE);
                                        if(this.room_overlap(getX(),getY()-h1,getX()+w1,getY())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room1);
                                        }
                                        break;
                                    case "Living room":
                                        Room room2=canvas.addRoom(getX(),getY()-h1,w1,h1,Color.YELLOW);
                                        if(this.room_overlap(getX(),getY()-h1,getX()+w1,getY())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room2);
                                        }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX(),getY()-h1,w1,h1,Color.GREEN);
                                    if(this.room_overlap(getX(),getY()-h1,getX()+w1,getY())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room3);
                                    }
                                    break;  
                                        
                                    default:
                                        break;
                                }
                                        
                                    break;
                                case "Centre":
                                switch (s1) {
                                    case "Bedroom":
                                        Room room=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()-h1,w1,h1,Color.RED);
                                        if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()-h1,getX()+(getWidth()+w1)/2,getY())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }
                                            
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()-h1,w1,h1,Color.BLUE);
                                    if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()-h1,getX()+(getWidth()+w1)/2,getY())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }

                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()-h1,w1,h1,Color.YELLOW);
                                    if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()-h1,getX()+(getWidth()+w1)/2,getY())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room2);
                                    }

                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()-h1,w1,h1,Color.GREEN);
                                    if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()-h1,getX()+(getWidth()+w1)/2,getY())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room3);
                                    }

                                        break;
                                    
                                    default:
                                        break;
                                }

                                    break;
                                case "Right":
                                switch (s1) {
                                    case "Bedroom":
                                        Room room=canvas.addRoom(getX()+getWidth()-w1,getY()-h1,w1,h1,Color.RED);
                                        if(this.room_overlap(getX()+getWidth()-w1,getY()-h1,getX()+getWidth(),getY())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }        
                                        break;
                                    case "Bathroom":
                                        Room room1=canvas.addRoom(getX()+getWidth()-w1,getY()-h1,w1,h1,Color.BLUE);
                                            if(this.room_overlap(getX()+getWidth()-w1,getY()-h1,getX()+getWidth(),getY())){
                                                JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                                this.delete(room1);
                                            }
                                        break;
                                    case "Living room":
                                        Room room2=canvas.addRoom(getX()+getWidth()-w1,getY()-h1,w1,h1,Color.YELLOW);
                                        if(this.room_overlap(getX()+getWidth()-w1,getY()-h1,getX()+getWidth(),getY())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room2);
                                        }

                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()+getWidth()-w1,getY()-h1,w1,h1,Color.GREEN);
                                        if(this.room_overlap(getX()+getWidth()-w1,getY()-h1,getX()+getWidth(),getY())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room3);
                                        }

                                        break;
                                    
                                    default:
                                        break;
                                }

                                    break;
                                
                                default:
                                    break;
                            }
                                break;
                            case "South":
                            switch (s3) {
                                case "Right":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()+getWidth()-w1,getY()+h1,w1,h1,Color.RED);
                                        if(this.room_overlap(getX()+getWidth()-w1,getY()+h1,getX()+getWidth(),getY()+2*h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }       
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()+getWidth()-w1,getY()+h1,w1,h1,Color.BLUE);
                                        if(this.room_overlap(getX()+getWidth()-w1,getY()+h1,getX()+getWidth(),getY()+2*h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room1);
                                        }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()+getWidth()-w1,getY()+h1,w1,h1,Color.YELLOW);
                                    if(this.room_overlap(getX()+getWidth()-w1,getY()+h1,getX()+getWidth(),getY()+2*h1)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room2);
                                    }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()+getWidth()-w1,getY()+h1,w1,h1,Color.GREEN);
                                    if(this.room_overlap(getX()+getWidth()-w1,getY()+h1,getX()+getWidth(),getY()+2*h1)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room3);
                                    }
                                        break;
                                    
                                    default:
                                        break;
                                }
                                    
                                    break;
                                case "Centre":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()+getHeight(),w1,h1,Color.RED);
                                        if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()+getHeight(),getX()+(getWidth()+w1)/2,getY()+getHeight()+h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }        
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()+getHeight(),w1,h1,Color.BLUE);
                                    if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()+getHeight(),getX()+(getWidth()+w1)/2,getY()+getHeight()+h1)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()+getHeight(),w1,h1,Color.YELLOW);
                                        if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()+getHeight(),getX()+(getWidth()+w1)/2,getY()+getHeight()+h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room2);
                                        }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()+(getWidth()-w1)/2,getY()+getHeight(),w1,h1,Color.GREEN);
                                        if(this.room_overlap(getX()+(getWidth()-w1)/2,getY()+getHeight(),getX()+(getWidth()+w1)/2,getY()+getHeight()+h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room3);
                                        }
                                        break;
                                    
                                    default:
                                        break;
                                }

                                    break;
                                case "Left":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX(),getY()+h1,w1,h1,Color.RED);
                                        if(this.room_overlap(getX(),getY()+h1,getX()+w1,getY()+2*h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }                                            
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX(),getY()+h1,w1,h1,Color.BLUE);
                                    if(this.room_overlap(getX(),getY()+h1,getX()+w1,getY()+2*h1)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX(),getY()+h1,w1,h1,Color.YELLOW);
                                        if(this.room_overlap(getX(),getY()+h1,getX()+w1,getY()+2*h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room2);
                                        }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX(),getY()+h1,w1,h1,Color.GREEN);
                                        if(this.room_overlap(getX(),getY()+h1,getX()+w1,getY()+2*h1)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room3);
                                        }
                                        break;
                                    
                                    default:
                                        break;
                                }

                                    break;
                            
                                default:
                                    break;
                            }

                            break;
                        default:
                            break;
                    }
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Canceled!");
                    }

                }
                if(s2=="East" || s2=="West"){
                    JPanel panel2 = new JPanel();
                    panel2.add(new JLabel("Select orientation:"));
                    panel2.add(d4);
                    panel2.add(new JLabel("Width:"));
                    JTextField t3 = new JTextField(5);
                    panel2.add(t3);
                    panel2.add(new JLabel("Height:"));
                    JTextField t4 = new JTextField(5);
                    panel2.add(t4);
                    int result2 = JOptionPane.showConfirmDialog(this, panel2, "Select Options", 
                                                         JOptionPane.OK_CANCEL_OPTION, 
                                                         JOptionPane.PLAIN_MESSAGE);
                    if (result2 == JOptionPane.OK_OPTION){
                        String s4= (String) d4.getSelectedItem();
                        int w2=Integer.parseInt(t3.getText());
                        int h2=Integer.parseInt(t4.getText());
                        switch (s2) {
                            case "East":
                            switch (s4) {
                                case "Top":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()+getWidth(),getY(),w2,h2,Color.RED);
                                        if(this.room_overlap(getX()+getWidth(),getY(),getX()+getWidth()+w2,getY()+h2)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }   
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()+getWidth(),getY(),w2,h2,Color.BLUE);
                                    if(this.room_overlap(getX()+getWidth(),getY(),getX()+getWidth()+w2,getY()+h2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()+getWidth(),getY(),w2,h2,Color.YELLOW);
                                        if(this.room_overlap(getX()+getWidth(),getY(),getX()+getWidth()+w2,getY()+h2)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room2);
                                        }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()+getWidth(),getY(),w2,h2,Color.GREEN);
                                        if(this.room_overlap(getX()+getWidth(),getY(),getX()+getWidth()+w2,getY()+h2)){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room3);
                                        }
                                        break;
                                
                                    default:
                                        break;
                                }
                                        
                                    break;
                                case "Centre":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()+getWidth(),getY()+(getHeight())/2-h2/2,w2,h2,Color.RED);
                                    if(this.room_overlap(getX()+getWidth(),getY()+(getHeight())/2+h2/2,getX()+getWidth()+w2,getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room);
                                    }
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()+getWidth(),getY()+(getHeight())/2-h2/2,w2,h2,Color.BLUE);
                                    if(this.room_overlap(getX()+getWidth(),getY()+(getHeight())/2+h2/2,getX()+getWidth()+w2,getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()+getWidth(),getY()+(getHeight())/2-h2/2,w2,h2,Color.YELLOW);
                                    if(this.room_overlap(getX()+getWidth(),getY()+(getHeight())/2+h2/2,getX()+getWidth()+w2,getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room2);
                                    }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()+getWidth(),getY()+(getHeight())/2-h2/2,w2,h2,Color.GREEN);
                                    if(this.room_overlap(getX()+getWidth(),getY()+(getHeight())/2+h2/2,getX()+getWidth()+w2,getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room3);
                                    }
                                        break;
                                
                                    default:
                                        break;
                                }

                                    break;
                                case "Bottom":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()+getWidth(),getY()+getHeight()-h2,w2,h2,Color.RED);
                                        if(this.room_overlap(getX()+getWidth(),getY()+getHeight()-h2,getX()+getWidth()+w2,getY()+getHeight())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room);
                                        }    
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()+getWidth(),getY()+getHeight()-h2,w2,h2,Color.BLUE);
                                    if(this.room_overlap(getX()+getWidth(),getY()+getHeight()-h2,getX()+getWidth()+w2,getY()+getHeight())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    } 
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()+getWidth(),getY()+getHeight()-h2,w2,h2,Color.YELLOW);
                                        if(this.room_overlap(getX()+getWidth(),getY()+getHeight()-h2,getX()+getWidth()+w2,getY()+getHeight())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room2);
                                        } 
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()+getWidth(),getY()+getHeight()-h2,w2,h2,Color.GREEN);
                                        if(this.room_overlap(getX()+getWidth(),getY()+getHeight()-h2,getX()+getWidth()+w2,getY()+getHeight())){
                                            JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                            this.delete(room3);
                                        } 
                                        break;
                                
                                    default:
                                        break;
                                }

                                    break;
                                
                                default:
                                    break;
                                }
                                break;
                            case "West":
                            switch (s4) {
                                case "Top":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()-w2,getY(),w2,h2,Color.RED);
                                    if(this.room_overlap(getX()-w2,getY(),getX(),getY()+h2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room);
                                    }   
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()-w2,getY(),w2,h2,Color.BLUE);
                                    if(this.room_overlap(getX()-w2,getY(),getX(),getY()+h2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()-w2,getY(),w2,h2,Color.YELLOW);
                                    if(this.room_overlap(getX()-w2,getY(),getX(),getY()+h2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room2);
                                    }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()-w2,getY(),w2,h2,Color.GREEN);
                                    if(this.room_overlap(getX()-w2,getY(),getX(),getY()+h2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room3);
                                    }
                                        break;
                                
                                    default:
                                        break;
                                }
                                    break;
                                case "Centre":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()-w2,getY()+(getHeight())/2-h2/2,w2,h2,Color.RED);
                                    if(this.room_overlap(getX()-w2,getY()+(getHeight())/2+h2/2,getX(),getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room);
                                    } 
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()-w2,getY()+(getHeight())/2-h2/2,w2,h2,Color.BLUE);
                                    if(this.room_overlap(getX()-w2,getY()+(getHeight())/2+h2/2,getX(),getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()-w2,getY()+(getHeight())/2-h2/2,w2,h2,Color.YELLOW);
                                    if(this.room_overlap(getX()-w2,getY()+(getHeight())/2+h2/2,getX(),getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room2);
                                    }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()-w2,getY()+(getHeight())/2-h2/2,w2,h2,Color.GREEN);
                                    if(this.room_overlap(getX()-w2,getY()+(getHeight())/2+h2/2,getX(),getY()+(getHeight())/2+ h2/2)){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room3);
                                    }
                                        break;
                                
                                    default:
                                        break;
                                }
                                    break;
                                case "Bottom":
                                switch (s1) {
                                    case "Bedroom":
                                    Room room=canvas.addRoom(getX()-w2,getY()+getHeight()-h2,w2,h2,Color.RED);
                                    if(this.room_overlap(getX()-w2,getY()+getHeight()-h2,getX(),getY()+getHeight())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room);
                                    }   
                                        break;
                                    case "Bathroom":
                                    Room room1=canvas.addRoom(getX()-w2,getY()+getHeight()-h2,w2,h2,Color.BLUE);
                                    if(this.room_overlap(getX()-w2,getY()+getHeight()-h2,getX(),getY()+getHeight())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room1);
                                    }
                                        break;
                                    case "Living room":
                                    Room room2=canvas.addRoom(getX()-w2,getY()+getHeight()-h2,w2,h2,Color.YELLOW);
                                    if(this.room_overlap(getX()-w2,getY()+getHeight()-h2,getX(),getY()+getHeight())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room2);
                                    }
                                        break;
                                    case "Dining":
                                    Room room3=canvas.addRoom(getX()-w2,getY()+getHeight()-h2,w2,h2,Color.GREEN);
                                    if(this.room_overlap(getX()-w2,getY()+getHeight()-h2,getX(),getY()+getHeight())){
                                        JOptionPane.showMessageDialog(this, "Room Overlap!!");
                                        this.delete(room3);
                                    }
                                        break;
                                
                                    default:
                                        break;
                                }
                                    break;
                            
                                default:
                                    break;
                            }

                                break;
                            default:
                                break;
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Canceled!");
                    }

                }

                
            } else {
                JOptionPane.showMessageDialog(this, "Canceled!");
            }
        });*/
        popup.add(addfurniture);
        addfurniture.addActionListener(e->{
            String[] bedroom={"Bed","Cupboard","Chair","Desk"};
            String[] bathroom={"Toilet","Sink","Shower"};
            String[] livingroom={"Sofa","TV"};
            String[] dining={"Table","Kitchentop"};
            JComboBox<String> bed= new JComboBox<>(bedroom);
            JComboBox<String> bath= new JComboBox<>(bathroom);
            JComboBox<String> living= new JComboBox<>(livingroom);
            JComboBox<String> dine= new JComboBox<>(dining);
            //System.out.println("entered here");
            if(x==Color.RED){
                JPanel panel = new JPanel();
                panel.add(bed);
                int result = JOptionPane.showConfirmDialog(this, panel, "Select Options", 
                                                         JOptionPane.OK_CANCEL_OPTION, 
                                                         JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String bed1=(String)bed.getSelectedItem();
                    switch (bed1) {
                        case "Bed":
                            addfurniture("./Furnitures/Bed/Bed.png","./Furnitures/Bed/Bed1.png",
                                    "./Furnitures/Bed/Bed2.png","./Furnitures/Bed/Bed3.png",50,60);
                            break;
                        case "Cupboard":
                            addfurniture("./Furnitures/Cupboard/Cupboard.png","./Furnitures/Cupboard/Cupboard1.png",
                                    "./Furnitures/Cupboard/Cupboard2.png","./Furnitures/Cupboard/Cupboard3.png",50,25);
                            break;
                        case "Chair":
                            addfurniture("./Furnitures/Chair/Chair.png","./Furnitures/Chair/Chair1.png",
                                    "./Furnitures/Chair/Chair2.png","./Furnitures/Chair/Chair3.png",30,30);
                            break;
                        case "Desk":
                            addfurniture("./Furnitures/Desk/Desk.png","./Furnitures/Desk/Desk1.png",
                                    "./Furnitures/Desk/Desk2.png","./Furnitures/Desk/Desk3.png",50,25);
                            break;
                        default:
                            break;
                    }
                }
            }
            if(x==Color.BLUE){
                JPanel panel1 = new JPanel();
                panel1.add(bath);
                int result1 = JOptionPane.showConfirmDialog(this, panel1, "Select Options", 
                                                         JOptionPane.OK_CANCEL_OPTION, 
                                                           JOptionPane.PLAIN_MESSAGE);
                if (result1 == JOptionPane.OK_OPTION) {
                String bath1=(String)bath.getSelectedItem();
                 switch (bath1) {
                      case "Toilet":
                         addfurniture("./Furnitures/Toilet/Toilet.png","./Furnitures/Toilet/Toilet1.png",
                                    "./Furnitures/Toilet/Toilet2.png","./Furnitures/Toilet/Toilet3.png",30,50);
                         break;
                     case "Sink":
                         addfurniture("./Furnitures/Sink/Sink.png","./Furnitures/Sink/Sink1.png",
                                    "./Furnitures/Sink/Sink2.png","./Furnitures/Sink/Sink3.png",35,22);
                         break;
                     case "Shower":
                         addfurniture("./Furnitures/Shower/Shower.png","./Furnitures/Shower/Shower1.png",
                                    "./Furnitures/Shower/Shower2.png","./Furnitures/Shower/Shower3.png",30,60);
                         break;
                     default:
                        break;
                }
            }
        }
            if(x==Color.YELLOW){
                JPanel panel2 = new JPanel();
                panel2.add(living);
                int result2 = JOptionPane.showConfirmDialog(this, panel2, "Select Options", 
                                                        JOptionPane.OK_CANCEL_OPTION, 
                                                        JOptionPane.PLAIN_MESSAGE);
                if (result2 == JOptionPane.OK_OPTION) {
                String living1=(String)living.getSelectedItem();
                switch (living1) {
                    case "Sofa":
                        addfurniture("./Furnitures/Sofa/Sofa.png","./Furnitures/Sofa/Sofa1.png",
                                    "./Furnitures/Sofa/Sofa2.png","./Furnitures/Sofa/Sofa3.png",75,45);
                        break;
                    case "TV":
                        addfurniture("./Furnitures/TV/TV.png","./Furnitures/TV/TV1.png",
                                    "./Furnitures/TV/TV2.png","./Furnitures/TV/TV3.png",60,30);
                        break;
                    default:
                        break;
                    }
            }
            }
            if(x==Color.GREEN){
                JPanel panel3 = new JPanel();
                panel3.add(dine);
                int result3 = JOptionPane.showConfirmDialog(this, panel3, "Select Options", 
                                                        JOptionPane.OK_CANCEL_OPTION, 
                                                        JOptionPane.PLAIN_MESSAGE);
                if (result3 == JOptionPane.OK_OPTION) {
                String dining1=(String)dine.getSelectedItem();
                switch (dining1) {
                    case "Table":
                        addfurniture("./Furnitures/Table/Table.png","./Furnitures/Table/Table1.png",
                                "./Furnitures/Table/Table2.png","./Furnitures/Table/Table3.png",50,50);
                        break;
                    case "Kitchentop":
                        addfurniture("./Furnitures/Kitchentop/Kitchentop.png","./Furnitures/Kitchentop/Kitchentop1.png",
                                    "./Furnitures/Kitchentop/Kitchentop2.png","./Furnitures/Kitchentop/Kitchentop3.png",150,37);
                        break;
                    default:
                        break;
                    }
            }
            }
        });
        addcorner(); 
        Room room1=this;
        // Mouse adapter functionality
        mouse = new MouseAdapter(){
            int gridSize=10;
            Point initialClick;
            Point initialPanelLocation;
            public void mousePressed(MouseEvent e){
                initialClick = e.getPoint();
                initialPanelLocation = getLocation();
                for(Room room:canvas.rooms){
                    room.lt.setVisible(false);
                    room.rb.setVisible(false);
                    room.setBorder(BorderFactory.createLineBorder(Color.BLACK,borderwidth,true));
                }
                lt.setVisible(true);
                rb.setVisible(true);
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,borderwidth,true));
                //System.out.println("mouse pressed on"+e.getComponent());
                SwingUtilities.invokeLater(() -> {
                    Container parent = getParent();
                    parent.setComponentZOrder(Room.this, 0); // Bring to front
                    parent.repaint(); // Repaint to reflect Z-order change
                });
            }
            public void mouseDragged(MouseEvent e){
                //Calculate the new location of the panel
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Move the panel by the difference in mouse positions
                int newX = Math.floorDiv(thisX+e.getX()-initialClick.x,gridSize)*gridSize;
                // find new y coord wtf gridsize
                int newY = Math.floorDiv(thisY+e.getY()-initialClick.y,gridSize)*gridSize;
                //System.out.println("coordinates"+newX+newY);
                
                // Set the new location of the panel
                setLocation(newX, newY);
                for (int i = room1.doors.size() - 1; i >= 0; i--) {
                    Door door = room1.doors.get(i);  // Get the room at index i
                    door.deletedoor();  // Delete the room from canvas and array
                }
                for (int i = room1.windows.size() - 1; i >= 0; i--) {
                    Window window = room1.windows.get(i);  // Get the room at index i
                    window.deletewindow();    // Delete the room from canvas and array
                }
                
                // ensuring the room being dragged is on top (it shouldn't overlap ideally)
                // this part will change too in the final version
                // TODO: MAKE THIS BETTER/ GET RID after overlap checks are in place
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                /*for (Room room : canvas.rooms){
                    System.out.println(room);
                }*/
                int nowX=getLocation().x;
                int nowY=getLocation().y;
                //System.out.println("coordinates"+nowX+nowY);
                if ((room_overlap(nowX,nowY,nowX+getWidth(),nowY+getHeight()))){
                    JOptionPane.showMessageDialog(canvas, "room overlap !!");
                    setLocation(initialPanelLocation);
                    // moving mouse to new location

                }
                

            }

            public void mouseClicked(MouseEvent e){
                // check for right click
                if (SwingUtilities.isRightMouseButton(e)){
                    popup.show(canvas,e.getComponent().getX(),e.getComponent().getY());
                    //System.out.println("RIGHT CLICKKKK");
                }

            }
        };
        addMouseListener(mouse); // to register clicks
        addMouseMotionListener(mouse); // to register drag
        popup.add(adddoor);
        adddoor.addActionListener(e->{
            Room room = this;
            String[] options={"North","South","East","West"};
            JComboBox<String> optionBox = new JComboBox<>(options);
            JPanel panel = new JPanel();
            panel.add(optionBox);
            int result = JOptionPane.showConfirmDialog(this, panel,"Select Options", 
                                                         JOptionPane.OK_CANCEL_OPTION, 
                                                         JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                String s=(String)optionBox.getSelectedItem();
                removeMouseListener(mouse);
                removeMouseMotionListener(mouse);    
                    mouse1=new MouseAdapter() {
                        Point min;
                        Point max;
                        public void mousePressed(MouseEvent e) {
                            min = e.getPoint(); 
                            //System.out.println("Mouse pressed at: " + min);
                        }
        
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            

                        }
                        public void mouseReleased(MouseEvent e){
                            max = e.getPoint();
                            //System.out.println("Mouse dragged to: " + max);
                            if(s=="North"){
                                min.y=0;
                                max.y=0;
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1);
                                Point test1=new Point(room.getX()+min.x-test.getX(),room.getY()+min.y-test.getY());
                                Point test2=new Point(room.getX()+max.x-test.getX(),room.getY()+max.y-test.getY());
                                if(room!=test){
                                adddoor(room,min,max,s);
                                adddoor(test,test1,test2,"South");
                                }
                                if(room==test&&room.x==Color.YELLOW){
                                adddoor(room,min,max,s);
                                }
                                
                            }
                            if(s=="South"){
                                min.y=room.getHeight();
                                max.y=room.getHeight();
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1);
                                Point test1=new Point(room.getX()+min.x-test.getX(),room.getY()+min.y-test.getY());
                                Point test2=new Point(room.getX()+max.x-test.getX(),room.getY()+max.y-test.getY());
                                if(room!=test){
                                    adddoor(room,min,max,s);
                                    adddoor(test,test1,test2,"North");
                                    }
                                if(room==test&&room.x==Color.YELLOW){
                                adddoor(room,min,max,s);
                                }
                            }
                            if(s=="West"){
                                min.x=0;
                                max.x=0;
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1);
                                Point test1=new Point(room.getX()+min.x-test.getX(),room.getY()+min.y-test.getY());
                                Point test2=new Point(room.getX()+max.x-test.getX(),room.getY()+max.y-test.getY());
                                if(room!=test){
                                    adddoor(room,min,max,s);
                                    adddoor(test,test1,test2,"East");
                                    }
                                if(room==test&&room.x==Color.YELLOW){
                                adddoor(room,min,max,s);
                                }
                            }
                            if(s=="East"){
                                min.x=room.getWidth();
                                max.x=room.getWidth();
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1);
                                Point test1=new Point(room.getX()+min.x-test.getX(),room.getY()+min.y-test.getY());
                                Point test2=new Point(room.getX()+max.x-test.getX(),room.getY()+max.y-test.getY());
                                if(room!=test){
                                    adddoor(room,min,max,s);
                                    adddoor(test,test1,test2,"West");
                                    }
                                    if(room==test&&room.x==Color.YELLOW){
                                    adddoor(room,min,max,s);
                                    }
                            }
                            removeMouseListener(mouse1);
                            removeMouseMotionListener(mouse1);
                            addMouseListener(mouse);
                            addMouseMotionListener(mouse);
                        }
                        
                    };
                    addMouseListener(mouse1);
                    addMouseMotionListener(mouse1);   
            }
            
        });
        popup.add(addwindow);
        addwindow.addActionListener(e->{
            Room room=this;
            String[] options={"North","South","East","West"};
            JComboBox<String> optionBox = new JComboBox<>(options);
            JPanel panel = new JPanel();
            panel.add(optionBox);
            int result = JOptionPane.showConfirmDialog(this, panel,"Select Options", 
                                                         JOptionPane.OK_CANCEL_OPTION, 
                                                         JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                String s=(String)optionBox.getSelectedItem();
                removeMouseListener(mouse);
                removeMouseMotionListener(mouse);    
                    mouse2=new MouseAdapter() {
                        Point min;
                        Point max;
                        public void mousePressed(MouseEvent e) {
                            min = e.getPoint(); 
                            //System.out.println("Mouse pressed at: " + min);
                        }
        
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            

                        }
                        public void mouseReleased(MouseEvent e){
                            max = e.getPoint();
                            //System.out.println("Mouse dragged to: " + max);
                            if(s=="North"){
                                min.y=0;
                                max.y=0;
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1);
                                if(room==test){
                                    addwindow(min,max,s);
                                }
                                
                            }
                            if(s=="South"){
                                min.y=room.getHeight();
                                max.y=room.getHeight();
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1);
                                if(room==test){
                                    addwindow(min,max,s);
                                }
                            }
                            if(s=="West"){
                                min.x=0;
                                max.x=0;
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1); 
                                if(room==test){
                                    addwindow(min,max,s);
                                }
                            }
                            if(s=="East"){
                                min.x=room.getWidth();
                                max.x=room.getWidth();
                                Point min1=new Point(room.getX()+min.x,room.getY()+min.y);
                                Point max1=new Point(room.getX()+max.x,room.getY()+max.y);
                                //System.out.println(min+","+max);
                                Room test=checkadjRoom(room,min1, max1);
                                if(room==test){
                                    addwindow(min,max,s);
                                }
                            }
                            removeMouseListener(mouse2);
                            removeMouseMotionListener(mouse2);
                            addMouseListener(mouse);
                            addMouseMotionListener(mouse);
                        }
                        
                    };
                    addMouseListener(mouse2);
                    addMouseMotionListener(mouse2);   
            }
        });
    }
    public void addfurniture(String filepath1,String filepath2,String filepath3,String filepath4, int w,int h){
        Furniture furniture= new Furniture(this,filepath1,filepath2,filepath3,filepath4);
        this.add(furniture);
        furnitures.add(furniture);
        this.revalidate();
        this.repaint();
        furniture.setBounds(0,0,w,h);

    }
    public void addfurniture(String filepath,int x,int y, int w,int h,String filepath1,String filepath2,String filepath3,String filepath4){
        Furniture furniture= new Furniture(this,filepath,filepath1,filepath2,filepath3,filepath4);
        furniture.existingfilepath=filepath;
        this.add(furniture);
        furnitures.add(furniture);
        this.revalidate();
        this.repaint();
        furniture.setBounds(x,y,w,h);

    }
    public Room checkadjRoom(Room room,Point a1,Point a2){
        for(Room room1:canvas.rooms){
            if(room1==room){
                continue;
            }
            if(a1.y==a2.y&&room.getY()==a1.y&&room1.getY()+room1.getHeight()==room.getY()
                &&Math.max(room.getX(),room1.getX())<a1.x&&a1.x<a2.x&&
                a2.x<Math.min(room.getX()+room.getWidth(),room1.getX()+room1.getWidth())){
                //System.out.println("north condition");
                return room1;
            }
            if(a1.y==a2.y&&room.getY()+room.getHeight()==a1.y&&room1.getY()==a1.y&&
                Math.max(room.getX(),room1.getX())<a1.x&&a1.x<a2.x&&
                a2.x<Math.min(room.getX()+room.getWidth(),room1.getX()+room1.getWidth())){
                //System.out.println("South condition");
                return room1;
            }
            if(a1.x==a2.x&&room.getX()==a1.x&&room1.getX()+room1.getWidth()==a1.x&&
                Math.max(room.getY(),room1.getY())<a1.y&&a1.y<a2.y&&
                a2.y<Math.min(room.getY()+room.getHeight(),room1.getY()+room1.getHeight())){
                //System.out.println("West condition");
                return room1;
            }
            if(a1.x==a2.x&&room.getX()+room.getWidth()==a1.x&&room1.getX()==a1.x&&
                Math.max(room.getY(),room1.getY())<a1.y&&a1.y<a2.y&&
                a2.y<Math.min(room.getY()+room.getHeight(),room1.getY()+room1.getHeight())){
                //System.out.println("East condition");
                return room1;
            }
            
        }
        return room;
    }
    public void adddoor(Room room,Point a1,Point a2,String s){
        Door door= new Door(room,s);
        room.add(door);
        room.doors.add(door);
        room.revalidate();
        room.repaint();
        switch (s) {
            case "North":
            door.setBounds(a1.x,0,a2.x-a1.x,borderwidth);
                break;
            case "South":
            door.setBounds(a1.x,room.getHeight()-borderwidth,a2.x-a1.x,borderwidth);
                break;
            case "West":
            door.setBounds(0,a1.y,borderwidth,a2.y-a1.y);
                break;
            case "East":
            door.setBounds(room.getWidth()-borderwidth,a1.y,borderwidth,a2.y-a1.y);
                break;
        
            default:
                break;
        }
        
    }
    public void adddoor(int x,int y,int w, int h,String s){
        Door door = new Door(this,s);
        door.side=s;
        this.add(door);
        doors.add(door);
        this.revalidate();
        this.repaint();
        door.setBounds(x,y,w,h);
    }
    public void addwindow(Point a1,Point a2,String s){
        Window window= new Window(this,s);
        this.add(window);
        windows.add(window);
        this.revalidate();
        this.repaint();
        //window.setBounds(x,y,w,h);
        switch (s) {
            case "North":
            window.setBounds(a1.x,0,a2.x-a1.x,borderwidth);
                break;
            case "South":
            window.setBounds(a1.x,getHeight()-borderwidth,a2.x-a1.x,borderwidth);
                break;
            case "West":
            window.setBounds(0,a1.y,borderwidth,a2.y-a1.y);
                break;
            case "East":
            window.setBounds(getWidth()-borderwidth,a1.y,borderwidth,a2.y-a1.y);
                break;
        
            default:
                break;
        }
    }
    public void addwindow(int x,int y, int w,int h,String s){
        Window window=new Window(this, s);
        this.add(window);
        windows.add(window);
        this.revalidate();
        this.repaint();
        window.setBounds(x,y,w,h);
    }
    public void delete(Room room){
        room.canvas.remove(room);
        room.canvas.rooms.remove(room);
        room.canvas.revalidate();
        room.canvas.repaint();
        //System.out.println("Room deleted");
    }
    public void rotate(){
        for (int i = doors.size() - 1; i >= 0; i--) {
            Door door = doors.get(i);  // Get the room at index i
            door.deletedoor();  // Delete the room from canvas and array
        }
        for (int i = windows.size() - 1; i >= 0; i--) {
            Window window = windows.get(i);  // Get the room at index i
            window.deletewindow();    // Delete the room from canvas and array
        }
    int initialw=getWidth();
    int initialh=getHeight();
    setBounds(getX(),getY(),getHeight(),getWidth());
    if(room_overlap(getX(), getY(), getX()+getWidth(), getY()+getHeight())){
        JOptionPane.showMessageDialog(canvas, "room overlap !!");
        setBounds(getX(),getY(),initialw,initialh);
    }
    canvas.revalidate();
    canvas.repaint();
    rb.setLocation(getWidth()-10-borderwidth,getHeight()-10-borderwidth);
    }
    public boolean room_overlap(int lt_roomX,int lt_roomY,int rb_roomX,int rb_roomY){
        boolean overlap1 = false;
        for(Room room : canvas.rooms){
            int lt_otherX = room.getX();
            int lt_otherY = room.getY();
            int rb_otherX = room.getX()+room.getWidth();
            int rb_otherY = room.getY()+room.getHeight();
            //System.out.println("Entered into for loop");
            if(room==this){
                //System.out.println("entered into this one");
                continue;
            }
            if (overlap(lt_roomX,lt_roomY,rb_roomX,rb_roomY,
                    lt_otherX,lt_otherY,rb_otherX,rb_otherY)){
                overlap1 = true;
                //System.out.println("entered into if condition1");
            }
            /*if(overlap(lt_otherX,lt_otherY,rb_otherX,rb_otherY,
                    lt_roomX,lt_roomY,rb_roomX,rb_roomY)){
                overlap1=true;
                System.out.println("entered into if condition2");
            }*/
            //System.out.println("boolean"+overlap1);
            
        }
        return overlap1;
    }
    public boolean window_overlap(int lt_roomX,int lt_roomY,int rb_roomX,int rb_roomY){
        boolean overlap=false;
        for(Window door:windows){
            int lt_otherX = door.getX();
            int lt_otherY = door.getY();
            int rb_otherX = door.getX()+door.getWidth();
            int rb_otherY = door.getY()+door.getHeight();
            if (overlap(lt_roomX,lt_roomY,rb_roomX,rb_roomY,
                    lt_otherX,lt_otherY,rb_otherX,rb_otherY)){
                overlap = true;
                //System.out.println("entered into if condition1");
            }
        }
        return overlap;
    }
    public boolean overlap(int ltx1, int lty1, int rbx1, int rby1,
    int ltx2, int lty2, int rbx2, int rby2) {
    if (rbx1 <= ltx2 || rbx2 <= ltx1) {
    return false; // No overlap
    }
    if (rby1 <= lty2 || rby2 <= lty1) {
    return false; // No overlap
    }
    return true;
    }
    public boolean door_overlap(int lt_roomX,int lt_roomY,int rb_roomX,int rb_roomY){
        boolean overlap=false;
        for(Door door:doors){
            int lt_otherX = door.getX();
            int lt_otherY = door.getY();
            int rb_otherX = door.getX()+door.getWidth();
            int rb_otherY = door.getY()+door.getHeight();
            if (overlap(lt_roomX,lt_roomY,rb_roomX,rb_roomY,
                    lt_otherX,lt_otherY,rb_otherX,rb_otherY)){
                overlap = true;
                //System.out.println("entered into if condition1");
            }
        }
        return overlap;
    }
    public void addcorner(){
        lt = new HotCorner(this,"lt",x);
        lt.setBounds(borderwidth, borderwidth,10,10);
        add(lt);
        rb = new HotCorner(this,"rb",x);
        rb.setBounds(getWidth()-10-borderwidth,getHeight()-10-borderwidth,10,10);
        add(rb);  
    }
}
// to enable resizing
class HotCorner extends JPanel{
    int gridSize = 10;

    public HotCorner(Room owner,String corner,java.awt.Color x){
        setBackground(Color.LIGHT_GRAY); // TODO: DELETE THIS LATER
        setVisible(false);

        // most of the work is done here:
        MouseAdapter hotcornermouse = new MouseAdapter(){
            final int minsize = 40;
            final int borderwidth = 2;

            int X;// mouse coords
            int Y;// mouse coords
            Point intitallocation;
            int initialwidth;
            int initialheight;
            public void mousePressed(MouseEvent e){
                // this function will let us know that the mouse pressed on the hotcorner and get it's coords
                X = e.getX();
                Y = e.getY();
                intitallocation=owner.getLocation();
                initialwidth=owner.getWidth();
                initialheight=owner.getHeight();
                //System.out.println("hotcorner presssed");
            }
            public void mouseDragged(MouseEvent e) {
                for (int i = owner.doors.size() - 1; i >= 0; i--) {
                    Door door = owner.doors.get(i);  // Get the room at index i
                    door.deletedoor();  // Delete the room from canvas and array
                }
                for (int i = owner.windows.size() - 1; i >= 0; i--) {
                    Window window = owner.windows.get(i);  // Get the room at index i
                    window.deletewindow();    // Delete the room from canvas and array
                }
                int newWidth;
                int newHeight;
                switch (corner){
                    // 2 cases depending on the corner
                    case "lt":
                        // figuring out new width and height
                         newWidth =owner.getWidth()+(X-e.getX()) ;
                         newHeight = owner.getHeight()+(Y-e.getY());
                         // minimum size for room
                         if (newWidth>minsize &&newHeight>minsize){
                             // setting new width and height
                             // changing location of origin
                             owner.setLocation(owner.getX()+e.getX()-X,owner.getY()+e.getY()-Y);
                             owner.setSize(newWidth,newHeight);
                             // changing location of rb hotcorner so it stays on the rb corner
                             // java is cool for doing this - i don't know what the fuck this is, I'm using aggregation to
                             // call an instance of this class and set it's location ;/ mind fuck
                             owner.rb.setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);

                         }else if (newWidth>minsize){

                             owner.setLocation(owner.getX()+e.getX()-X,owner.getY()+(owner.getHeight()-minsize));
                             owner.setSize(newWidth,minsize);

                             owner.rb.setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);
                         }else if (newHeight>minsize){
                             owner.setLocation(owner.getX()+(owner.getWidth()-minsize),owner.getY()+e.getY()-Y);
                             owner.setSize(minsize,newHeight);

                             owner.rb.setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);
                         }



                        break;
                    case "rb":
                        // when rb is pulled, origin will stay fixed
                        // new width and height
                         newWidth = owner.getWidth()-(X-e.getX());
                         newHeight = owner.getHeight()-(Y-e.getY());
                         if (newWidth>minsize &&newHeight>minsize){
                             owner.setSize(newWidth,newHeight);
                             // moving rb around to make sure it stays on the corner

                         }else if (newWidth>minsize){
                             owner.setSize(newWidth,minsize);
                         }else if(newHeight>minsize){
                             owner.setSize(minsize,newHeight);
                         }
                            setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);

                        break;
                }
                // bringing the room in question up so it's easier to work with
                // TODO: DELETE THIS LATER
                owner.setComponentZOrder(e.getComponent(),0);


            }
            // this function deals with snapping to the grid and yeah that's pretty much it.
            public void mouseReleased(MouseEvent e){
                int newYcoord;
                int newXcoord;
                int newWidth;
                int newHeight;
                switch(corner){
                    //we want the corners to snap inwards, i.e the area of the room should only get smaller
                    // as opposed to bigger because I believe it'll be easier on the overlap checker if we do this
                    // also decreases the likelihood of the overlap checker being called in the first place
                    case "lt":
                         newYcoord = Math.floorDiv(owner.getY(),gridSize)*gridSize;
                         if(newYcoord<owner.getY()){
                             newYcoord +=gridSize;
                         }
                         newXcoord = Math.floorDiv(owner.getX(),gridSize)*gridSize;
                        if(newXcoord<owner.getX()){
                            newXcoord +=gridSize;
                        }

                         newWidth = owner.getWidth()+(owner.getX()-newXcoord);
                         newHeight = owner.getHeight()+(owner.getY()-newYcoord);
                        // TODO :OVERLAP CHECK
                        if (newWidth>20 && newHeight>20){
                            owner.setLocation(newXcoord,newYcoord);
                            owner.setSize(newWidth,newHeight);
                            owner.rb.setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);
                            if(owner.room_overlap(newXcoord, newYcoord, newXcoord+newWidth, newYcoord+newHeight)){
                                JOptionPane.showMessageDialog(owner.canvas, "Room overlap !!");
                                owner.setLocation(intitallocation);
                                owner.setSize(initialwidth,initialheight);
                                owner.rb.setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);
                            }
                            // setting rb to the right bottom corner yet again- yes it's pretty annoying,
                            // and yes it's necessary because we have to change the width of the room when we're pulling lt
                            
                            owner.canvas.revalidate();
                            owner.canvas.repaint();
                        }else{
                      
                        }

                        break;
                    case "rb":
                        newWidth = Math.floorDiv(owner.getWidth(),gridSize)*gridSize;
                        newHeight= Math.floorDiv(owner.getHeight(),gridSize)*gridSize;
                        owner.setSize(newWidth,newHeight);
                        setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);
                        if(owner.room_overlap(owner.getX(), owner.getY(),owner.getX()+ newWidth,owner.getY()+ newHeight)){
                            JOptionPane.showMessageDialog(owner.canvas, "Room overlap !!");
                            owner.setSize(initialwidth,initialheight);
                            setLocation(owner.getWidth()-gridSize-borderwidth,owner.getHeight()-gridSize-borderwidth);
                        }
                        
                        owner.canvas.revalidate();
                        owner.canvas.repaint();
                }

            }
        };
        addMouseListener(hotcornermouse);
        addMouseMotionListener(hotcornermouse);
    }
}

