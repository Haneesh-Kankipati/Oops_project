import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Window extends JPanel{
    Room room1;
    String side;
    int stripeWidth=5;
    MouseAdapter mouse;
    public Window(Room room,String s){
        room1=room;
        side=s;
        setLayout(null);
        setVisible(true);
        mouse = new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e)){
                    deletewindow();
                }
            }
        };
        addMouseListener(mouse);
        if(room1.door_overlap(getX(), getY(), getX()+getWidth(), getY()+getHeight())){
            JOptionPane.showMessageDialog(room1, "Invalid placement");
            deletewindow();
        }

    }
    public void deletewindow(){
        room1.remove(this);
        room1.windows.remove(this);
        room1.revalidate();
        room1.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call the superclass method first

        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Draw alternate black and white stripes
        if(side.equals("North")||side.equals("South")){
        for (int x = 0; x < panelWidth; x += stripeWidth) {
            if ((x / stripeWidth) % 2 == 0) {
                g2d.setColor(Color.BLACK);
            } else {
                g2d.setColor(room1.x);
            }
            g2d.fillRect(x, 0, stripeWidth, panelHeight);
        }
        }
        else{
            for (int x = 0; x < panelHeight; x += stripeWidth) {
                if ((x / stripeWidth) % 2 == 0) {
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(room1.x);
                }
                g2d.fillRect(0, x, panelWidth, stripeWidth);
            } 
        }
    }
}
