import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Random;

class Canvas extends JPanel {
    boolean wrt_room=false;
    int[] room_coords={-1,-1};
    int borderwidth=3;
    ArrayList<Room> rooms;
    int gridsize=10;
    JScrollPane scrollPane;
    public Canvas(){
        rooms = new ArrayList<Room>();
        setLayout(null);
        setSize(600,500);
        setBackground(Color.GRAY);
        setVisible(true);
        setBounds(0,30,600,500);
        
        
        MouseAdapter mouse=new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                for(Room room:rooms){
                    room.lt.setVisible(false);
                    room.rb.setVisible(false);
                    room.setBorder(BorderFactory.createLineBorder(Color.BLACK,borderwidth,true));
                }
            }
        };
        addMouseListener(mouse); // to register clicks
        addMouseMotionListener(mouse);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting

        // Set the color for the dots
        g.setColor(Color.LIGHT_GRAY);

        // Draw the dot grid
        for (int x = 0; x < getWidth(); x += gridsize) {
            for (int y = 0; y < getHeight(); y += gridsize) {
                g.fillOval(x - 1, y - 1, 2, 2); // Draw a dot (4x4 pixels)
            }
        }
    }
    public void addRoom(java.awt.Color x) {
        Room room = new Room(x, this);
        room.x=x;
        //System.out.println(x.getRed()+","+x.getGreen()+","+x.getBlue());
        //Random rand = new Random();
        int[] coords;
        //int x_coord = Math.floorDiv(rand.nextInt(0,getWidth()-100),10)*10;
        //int y_coord = Math.floorDiv(rand.nextInt(0,getHeight()-100),10)*10;
        if (wrt_room) { // if x coordinate of room coords is -1 it means that no specific value
            // has been assigned.
            coords = room_coords;
            //System.out.println(coords[0] + " " + coords[1] + " canvasclass room coords added");
            wrt_room = false;
        } else {
            coords = rowmajorcoords();
            //System.out.println("added normally");
        }


        int x_coord = coords[0];
        int y_coord = coords[1];

        //System.out.println("\n\n"+x_coord + " " + y_coord + "  room added here\n\n");
        room.setBounds(x_coord, y_coord, 100, 50);

        //setComponentZOrder(this, 0);

            if(!room.room_overlap(room.getX(),room.getY(),room.getX()+room.getWidth(),room.getY()+room.getHeight())){
                this.add(room);
                rooms.add(room);
            }else{
                JOptionPane.showMessageDialog(this,"Make space for room!!");
            }




        // essentially refresh the canvas to show the updated elements
        this.revalidate();
        //setComponentZOrder(room,0);
        this.repaint();

        //rooms.add(room);
    }
    public int[] rowmajorcoords() {
        for(int j=0;j<410;j+=60){
        for (int i = 10; i < 910; i += 10) {
            boolean present = false;
            for (Room room : rooms) {
                if (room.getY() != j) {
                    // we only care about the top most rooms
                    continue;
                }
                if (room.getX() == i) {
                    i += room.getWidth(); // 90 and not a 100 because 10 will be appended anyway after the next iteration
                    present = true;
                    break;
                }
            }
            Room test = new Room(Color.BLACK, this);
            test.setBounds(i, j, 100, 50);
            if (test.room_overlap(test.getX(),test.getY(),test.getX()+test.getWidth(),test.getY()+test.getHeight())) {
                present = true;
            }
            if (!present) {
                return new int[]{i, j};
            }
        }
    }
    return new int[]{-1,0};
    }
    public Room addRoom(int x,int y, int w, int h,java.awt.Color xColor){
        Room room1 = new Room(xColor,this);
        room1.x=xColor;
        this.add(room1);
        room1.setBounds(x,y,w,h);
        room1.addcorner();
        this.revalidate();
        this.repaint();
        rooms.add(room1);
        setComponentZOrder(room1,0);
        return room1;
    }
    public void clear(){
        for (int i = rooms.size() - 1; i >= 0; i--) {
            Room room = rooms.get(i);  // Get the room at index i
            room.delete(room);  // Delete the room from canvas and array
        }
    }
    public String storedata(){
        StringBuilder string = new StringBuilder();
        for(Room room:rooms){   
            string.append(room.getX()+","+room.getY()+","+room.getWidth()+","+room.getHeight()+","+room.color);
            string.append("\n");
            for(Furniture furniture:room.furnitures){
                string.append(furniture.getX()+","+furniture.getY()+","+furniture.getWidth()+","+
                                furniture.getHeight()+","+furniture.existingfilepath+","+furniture.filepath11+","+
                                furniture.filepath22+","+furniture.filepath33+","+furniture.filepath44+",");
            }
            string.append("\n");
            for(Door door:room.doors){
                string.append(door.getX()+","+door.getY()+","+door.getWidth()+","+door.getHeight()+","+
                                door.side+",");
            }
            string.append("\n");
            for(Window window:room.windows){
                string.append(window.getX()+","+window.getY()+","+window.getWidth()+","+window.getHeight()+","+
                window.side+",");
            }
            string.append("\n");
        }
        String str= string.toString();
        //System.out.println(str);
        return str;
    }

    public static void main(String[]kwargs){
         class tempFrame extends JFrame{
            File currentfile;
            Canvas canvasClass;
             public tempFrame(){
                 setTitle("2DFloorPlan");
                 setSize(1000,500);
                 setLayout(new FlowLayout());
                 //setBounds(0,0,1000,500);
                 setVisible(true);
                 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                 // creating canvas object
                 canvasClass = new Canvas();
                 // Buttons
                 JPanel filebut = new JPanel();
                 add(filebut);
                 filebut.setBackground(Color.CYAN);
                 JButton save = new JButton("Save");
                 filebut.add(save);
                 JButton open = new JButton("Open");
                 filebut.add(open);
                 JButton newfile = new JButton("New File");
                 filebut.add(newfile);
                 JPanel store= new JPanel();
                 add(store);
                 store.setBackground(Color.CYAN);
                 JButton redRoom = new JButton("Bedroom");
                 store.add(redRoom);
                 //redRoom.setBounds(0,0,10,20);
                 JButton blueRoom = new JButton("Bathroom");
                 store.add(blueRoom);
                 //blueRoom.setBounds(10,0,10,20);
                 JButton yellowRoom= new JButton("Living room");
                 store.add(yellowRoom);
                 JButton orangeRoom= new JButton("Dining");
                 store.add(orangeRoom);
                 // Adding canvas to JFrame
                 canvasClass.setPreferredSize(new Dimension(1000,500));
                 add(canvasClass);
                 newfile.addActionListener(e->newFile());
                 save.addActionListener(e->saveFile());
                 open.addActionListener(e->openFile());
                // Button elements
                 redRoom.addActionListener(e -> {
                     canvasClass.addRoom(Color.RED);
                     //System.out.println("  Room added");
                 });
                 blueRoom.addActionListener(e -> {
                     canvasClass.addRoom(Color.BLUE);
                     //System.out.println("  Room added");
                 });
                 yellowRoom.addActionListener(e->{
                    canvasClass.addRoom(Color.YELLOW);
                 });
                 orangeRoom.addActionListener(e->{
                    canvasClass.addRoom(Color.GREEN);
                 });
                pack();
             }
    private void newFile(){
            canvasClass.clear();
            currentfile = null;
               
        }         
    private void writeToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(canvasClass.storedata());
            JOptionPane.showMessageDialog(this, "File saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving file.");
        }
    }
    private void saveFile() {
        // If currentFile is null, provide a predefined directory to save files
        if (currentfile == null) {
            JFileChooser fileChooser = new JFileChooser();
    
            // Set the default directory (path) where files will be saved
            File defaultDirectory = new File("../Oops project");  // Change this to your desired folder
            if (!defaultDirectory.exists()) {
                defaultDirectory.mkdirs();  // Ensure the directory exists
            }
            
            fileChooser.setCurrentDirectory(defaultDirectory);  // Set the default directory in the file chooser
    
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentfile = fileChooser.getSelectedFile();
                writeToFile(currentfile);  // Save the color to the selected file
            }
        } else {
            writeToFile(currentfile);  // If currentFile is not null, just save to the existing file
        }
    }
    private void readFromFile(File file) {
        try {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) {
            continue; // Skip empty lines
        }
        // Read and parse room data
        String[] roomData = line.split(",");
        Color color=null;
        //System.out.println(roomData);
        if(roomData[4].trim().equals("Red")){
            color=Color.RED;
        }
        if(roomData[4].trim().equals("Blue")){
            color=Color.BLUE;
        }
        if(roomData[4].trim().equals("Yellow")){
            color=Color.YELLOW;
        }
        if(roomData[4].trim().equals("Green")){
            color=Color.GREEN;
        }
        Room roomtest = canvasClass.addRoom(
            Integer.parseInt(roomData[0].trim()),
            Integer.parseInt(roomData[1].trim()),
            Integer.parseInt(roomData[2].trim()),
            Integer.parseInt(roomData[3].trim()),
            color
        );
        // Read furniture data
        line = reader.readLine();
        if (line != null && !line.trim().isEmpty()) {
            String[] furnitureData = line.split(",");
            for (int i = 0; i < furnitureData.length; i += 9) {
                String filePath = furnitureData[i + 4].trim();
                String filePath1 = furnitureData[i + 5].trim();
                String filePath2= furnitureData[i + 6].trim();
                String filePath3= furnitureData[i + 7].trim();
                String filePath4= furnitureData[i + 8].trim();
                roomtest.addfurniture(
                    filePath,
                    Integer.parseInt(furnitureData[i].trim()),
                    Integer.parseInt(furnitureData[i + 1].trim()),
                    Integer.parseInt(furnitureData[i + 2].trim()),
                    Integer.parseInt(furnitureData[i + 3].trim()),
                    filePath1,filePath2,filePath3,filePath4
                );
            }
        }
        // Read door data
        line = reader.readLine();
        if (line != null && !line.trim().isEmpty()) {
            String[] doorData = line.split(",");
            for (int i = 0; i < doorData.length; i += 5) {
                roomtest.adddoor(
                    Integer.parseInt(doorData[i].trim()),
                    Integer.parseInt(doorData[i + 1].trim()),
                    Integer.parseInt(doorData[i + 2].trim()),
                    Integer.parseInt(doorData[i + 3].trim()),
                    doorData[i + 4].trim()
                );
            }
        }
        // Read window data
        line = reader.readLine();
        if (line != null && !line.trim().isEmpty()) {
            String[] windowData = line.split(",");
            for (int i = 0; i < windowData.length; i += 5) {
                roomtest.addwindow(
                    Integer.parseInt(windowData[i].trim()),
                    Integer.parseInt(windowData[i + 1].trim()),
                    Integer.parseInt(windowData[i + 2].trim()),
                    Integer.parseInt(windowData[i + 3].trim()),
                    windowData[i + 4].trim()
                );
            }
        }
    }
    reader.close();
} catch (IOException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Error opening file.");
}

    }
    private void openFile() {
        canvasClass.clear();
        JFileChooser fileChooser = new JFileChooser();
    
        // Set the default directory (path) where the file chooser will open
        File defaultDirectory = new File("../Oops project");  // Change this to your desired folder
        if (!defaultDirectory.exists()) {
            defaultDirectory.mkdirs();  // Ensure the directory exists
        }
    
        fileChooser.setCurrentDirectory(defaultDirectory);  // Set the default directory in the file chooser
    
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentfile = fileChooser.getSelectedFile();
            readFromFile(currentfile);  // Open and read the color from the selected file
        }
    }
        }
        new tempFrame();

    }

}

