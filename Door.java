import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Door extends JPanel {
    Room room1;
    int borderwidth=3;
    MouseAdapter mouse;
    String side;
    public Door(Room room,String s){
        room1=room;
        side=s;
        setLayout(null);
        setVisible(true);
        setBackground(room.x);
        mouse = new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e)){
                    deletedoor();
                }
            }
        };
        addMouseListener(mouse);
        if(room1.window_overlap(getX(), getY(), getX()+getWidth(), getY()+getHeight())){
            JOptionPane.showMessageDialog(room1, "Invalid placement");
            deletedoor();
        }
    }
    public void deletedoor(){
        Door test = checkadjDoor(this);
        if(this==test){
            room1.remove(this);
            room1.doors.remove(this);
            room1.revalidate();
            room1.repaint();
            //System.out.println("entered if");
        }
        else{
            room1.remove(this);
            room1.doors.remove(this);
            room1.revalidate();
            room1.repaint();
            test.room1.remove(test);
            test.room1.doors.remove(test);
            test.room1.revalidate();
            test.room1.repaint();
           //System.out.println("Entered else");
        }
    }
    public Door checkadjDoor(Door door){
           Point test1= new Point(door.room1.getX()+door.getX(),door.room1.getY()+door.getY());
           Point check1=new Point(0,0);
           Point check2=new Point(0,0); 
           switch (door.side) {
            case "North":
            check1= new Point(door.room1.getX()+door.getX(),door.room1.getY()+door.getY());
            check2=new Point(door.room1.getX()+door.getX()+door.getWidth(),door.room1.getY()+door.getY());   
                break;
            case "South":  
            check1= new Point(door.room1.getX()+door.getX(),door.room1.getY()+door.getY()+borderwidth);
            check2=new Point(door.room1.getX()+door.getX()+door.getWidth(),door.room1.getY()+door.getY()+borderwidth); 
                break;
            case "East":
            check1= new Point(door.room1.getX()+door.getX()+borderwidth,door.room1.getY()+door.getY());
            check2=new Point(door.room1.getX()+door.getX()+door.getWidth(),door.room1.getY()+door.getY()+door.getHeight());
                break;
            case"West":
            check1= new Point(door.room1.getX()+door.getX(),door.room1.getY()+door.getY());
            check2= new Point(door.room1.getX()+door.getX(),door.room1.getY()+door.getY()+door.getHeight());
                break;
            default:
                break;
           }          
           Room test=door.room1.checkadjRoom(door.room1,check1,check2);
           //System.out.println(test);
           for(Door door1:test.doors){
            Point test3=new Point(door1.room1.getX()+door1.getX(),door1.room1.getY()+door1.getY());
            //Point test4 =new Point(door1.room1.getX()+door1.getX()+door1.getWidth(),door1.room1.getY()+door1.getY()+door1.getHeight());
                if(test1.x==test3.x&&test1.y==test3.y+borderwidth){
                    //System.out.println("north conditon");
                    return door1;
                }
                if(test1.x==test3.x&&test1.y+borderwidth==test3.y){
                    return door1;
                }
                if(test1.y==test3.y&&test1.x==test3.x+borderwidth){
                    return door1;
                }
                if(test1.y==test3.y&&test1.x+borderwidth==test3.x){
                    return door1;
                }
           }
        return door;
    }
}
