import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
public class Furniture extends JPanel{
    double rotationAngle=0;
    Image image;
    Room room1;
    String existingfilepath;
    String filepath11;
    String filepath22;
    String filepath33;
    String filepath44;
    JMenuItem rotate;
    JMenuItem delete;
    JPopupMenu popup = new JPopupMenu();
    int originalwidth=getWidth();
    int originalheight=getHeight();
    public Furniture(Room room,String filepath1,String filepath2,String filepath3,String filepath4){
        room1=room;
        existingfilepath=filepath1;
        filepath11=filepath1;
        filepath22=filepath2;
        filepath33=filepath3;
        filepath44=filepath4;
        rotate= new JMenuItem("Rotate");
        delete=new JMenuItem("Delete");
        setLayout(null);
        setVisible(true);
        try {
            
            image = ImageIO.read(new File(existingfilepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popup.add(rotate);
        rotate.addActionListener(e->{
            rotateClockwise();
        });
        popup.add(delete);
        delete.addActionListener(e->{
            room.remove(this);
            room.furnitures.remove(this);
            room.revalidate();
            room.repaint();
        });

        MouseAdapter mouse = new MouseAdapter(){
            //int gridSize=10;
            Point initialClick;
            Point initialPanelLocation;
            public void mousePressed(MouseEvent e){
                initialClick = e.getPoint();
                initialPanelLocation = getLocation();
                
                
            }
            public void mouseDragged(MouseEvent e){
                //Calculate the new location of the panel
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Move the panel by the difference in mouse positions
                int newX = thisX+e.getX()-initialClick.x;
                // find new y coord wtf gridsize
                int newY = thisY+e.getY()-initialClick.y;
                //System.out.println("coordinates"+newX+newY);
                
                // Set the new location of the panel
                setLocation(newX, newY);
                
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
                if ((room_overlap(nowX,nowY,nowX+getWidth(),nowY+getHeight()))){
                    JOptionPane.showMessageDialog(room, "Furniture overlap !!");
                    setLocation(initialPanelLocation);
                    // moving mouse to new location

                }
                //System.out.println("coordinates"+nowX+nowY);
                

            }

            public void mouseClicked(MouseEvent e){
                // check for right click
                if (SwingUtilities.isRightMouseButton(e)){
                    popup.show(room,e.getComponent().getX(),e.getComponent().getY());
                    //System.out.println("RIGHT CLICKKKK");
                }

            }
        };
        addMouseListener(mouse); // to register clicks
        addMouseMotionListener(mouse);

    }
    public Furniture(Room room,String filepath,String filepath1,String filepath2,String filepath3,String filepath4){
        room1=room;
        existingfilepath=filepath;
        filepath11=filepath1;
        filepath22=filepath2;
        filepath33=filepath3;
        filepath44=filepath4;
        if(existingfilepath.equals(filepath11)){
            rotationAngle=0;
        }
        if(existingfilepath.equals(filepath22)){
            rotationAngle=Math.PI/2;
        }
        if(existingfilepath.equals(filepath33)){
            rotationAngle=Math.PI;
        }
        if(existingfilepath.equals(filepath44)){
            rotationAngle=Math.PI*1.5;
        }
        rotate= new JMenuItem("Rotate");
        delete=new JMenuItem("Delete");
        setLayout(null);
        setVisible(true);
        try {
            
            image = ImageIO.read(new File(existingfilepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popup.add(rotate);
        rotate.addActionListener(e->{
            rotateClockwise();
        });
        popup.add(delete);
        delete.addActionListener(e->{
            room.remove(this);
            room.furnitures.remove(this);
            room.revalidate();
            room.repaint();
        });

        MouseAdapter mouse = new MouseAdapter(){
            //int gridSize=10;
            Point initialClick;
            Point initialPanelLocation;
            public void mousePressed(MouseEvent e){
                initialClick = e.getPoint();
                initialPanelLocation = getLocation();
                
                
            }
            public void mouseDragged(MouseEvent e){
                //Calculate the new location of the panel
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Move the panel by the difference in mouse positions
                int newX = thisX+e.getX()-initialClick.x;
                // find new y coord wtf gridsize
                int newY = thisY+e.getY()-initialClick.y;
                //System.out.println("coordinates"+newX+newY);
                
                // Set the new location of the panel
                setLocation(newX, newY);
                
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
                if ((room_overlap(nowX,nowY,nowX+getWidth(),nowY+getHeight()))){
                    JOptionPane.showMessageDialog(room, "Furniture overlap !!");
                    setLocation(initialPanelLocation);
                    // moving mouse to new location

                }
                //System.out.println("coordinates"+nowX+nowY);
                

            }

            public void mouseClicked(MouseEvent e){
                // check for right click
                if (SwingUtilities.isRightMouseButton(e)){
                    popup.show(room,e.getComponent().getX(),e.getComponent().getY());
                    //System.out.println("RIGHT CLICKKKK");
                }

            }
        };
        addMouseListener(mouse); // to register clicks
        addMouseMotionListener(mouse);
    }
    public void rotateClockwise() {
        rotationAngle += Math.PI / 2; // Increment by 90 degrees (π/2 radians)
        if (rotationAngle >= 2 * Math.PI) {
            // Reset the angle after 360 degrees
            rotationAngle = 0;
        }
        setBounds(getX(), getY(), getHeight(), getWidth()); // Swap width and height
        if(rotationAngle==0){
            existingfilepath=filepath11;
            try {
            
                image = ImageIO.read(new File(existingfilepath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(rotationAngle==Math.PI/2){
            existingfilepath=filepath22;
            try {
            
                image = ImageIO.read(new File(existingfilepath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(rotationAngle==Math.PI){
            existingfilepath=filepath33;
            try {
            
                image = ImageIO.read(new File(existingfilepath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(rotationAngle==Math.PI*1.5){
            existingfilepath=filepath44;
            try {
            
                image = ImageIO.read(new File(existingfilepath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        revalidate(); // Re-layout the panel
        repaint();    // Repaint to reflect changes
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear previous content
    
        Graphics2D g2d = (Graphics2D) g;
        
    
        g2d.drawImage(image, 0,0,getWidth(),getHeight(), this);
    
        // Reset transformations for any additional graphics
        g2d.dispose();
    }
    
    
    public boolean room_overlap(int lt_roomX,int lt_roomY,int rb_roomX,int rb_roomY){
        boolean overlap1 = false;
        for(Furniture room : room1.furnitures){
            int lt_otherX = room.getX();
            int lt_otherY = room.getY();
            int rb_otherX = room.getX()+room.getWidth();
            int rb_otherY = room.getY()+room.getHeight();
            //System.out.println("Entered into for loop");
            if(room==this){
                //System.out.println("entered into this one");
                continue;
            }
            if (room1.overlap(lt_roomX,lt_roomY,rb_roomX,rb_roomY,
                    lt_otherX,lt_otherY,rb_otherX,rb_otherY)){
                overlap1 = true;
                //System.out.println("entered into if condition1");
            }
            
            
        }
        return overlap1;
    }
    
}
    