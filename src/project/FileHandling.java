//João Craveiro 2013136429
//João Faria 2013136446 

package project;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileHandling {

    /**
     * Constructor of the class.
     */
    public FileHandling() {};

    /**
     * Private buffered reader.
     */
    private BufferedReader fR;

    /**
     * private buffered writer.
     */
    private BufferedWriter fW;


    /**
     * Method that opens the file in read mode and initializes the buffered reader.
     * @param fileName Name of file to be opened.
     * @return This method returns -1 if the file doesn't exist or 1 in case of success.
     * @throws IOException
     */
    public int openFileRead(String fileName) throws IOException {
        try{
        fR = new BufferedReader(new FileReader(fileName));
        } catch(FileNotFoundException e){
            return -1;
        }
        return 1;
    }

    /**
     * Method that opens the file in write mode and initializes the buffered writer.
     * @param fileName Name of file to be opened.
     * @throws IOException
     */
    public void openFileWrite(String fileName) throws IOException {
        fW = new BufferedWriter(new FileWriter(fileName));
    }

    /**
     * Method that opens the file in append mode and initializes the buffered writer.
     * @param fileName Name of file to be opened.
     * @throws IOException
     */
    public void openFileAppend(String fileName) throws IOException {
        fW = new BufferedWriter(new FileWriter(fileName, true));
    }

    /**
     * Method that closes the File and the buffered reader.
     * @throws IOException
     */
    public void closeRead() throws IOException {
        fR.close();
    }

    /**
     * Method that closes the File and the buffered writer.
     * @throws IOException
     */
    public void closeWrite() throws IOException {
        fW.close();
    }

    /**
     * Method to write a line to a File opened in the buffered writer.
     * @param line Line that will be written to the file.
     * @throws IOException
     */
    public void writeLine(String line) throws IOException {
        fW.write(line,0,line.length());
        fW.newLine();
    }
    
    /**
     * Checks if the string is a number.If it is returns true else returns false.
     * @param str
     * @return
     */
    public static boolean isNumber(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }

    /**
     * Method that reads the config file and initializes all the components.
     * It starts by opening the config file and read it line by line.
     * In the first iteration it initializes the environment, in the following iterations it adds the entities to the environment.
     * @param fileName Name of the config file.
     * @param environment Environment that will be configured.
     * @return This method returns -1 if openFileRead returns -1 or 1 in case of success.
     * @throws IOException
     */
    public int readConfig(String fileName, Environment environment) throws IOException {
        int i = 0;

        if(openFileRead(fileName) != -1){
            while (fR.ready()) {
                String line = fR.readLine();
                if(i == 0) {
                    initEnvironment(line, environment);
                } else {
                    initEntity(line, environment);
                }
                i++;
            }
            closeRead();
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Method that reads the configuration of the environment and updates it.
     * @param line Line that contains the data that was read from the config file.
     * @param environment Environment that is being configured.
     */
    private void initEnvironment(String line, Environment environment) {
        int x, y;
        StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

        x = Integer.parseInt(stringTokenizer.nextElement().toString());
        y = Integer.parseInt(stringTokenizer.nextElement().toString());

        environment.setX(x);
        environment.setY(y);

    }

    /**
     * This methods realizes the parsing of a line and adds to the environment the correct entity.
     * All the information in the configuration file is separated by a comma so this method uses a tokenizer to split the line extract the relevant information and adds to the environment the object contained in the line.
     * @param line Line that contains the configuration of an entity.
     * @param environment Environment that is being configured and where we will add the entity.
     */
    private void initEntity(String line, Environment environment) {

        int FOV = 0;
        int maxHops = 0;
        int x;
        int y;
        String type = null;

        StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

        while (stringTokenizer.hasMoreElements()) {
            String objectClass = stringTokenizer.nextElement().toString();

            if ((objectClass.equals("RandomStrat")) || (objectClass.equals("Hamming")) || (objectClass.equals("Closest")) || (objectClass.equals("InanimateObject"))) {
                String colour = stringTokenizer.nextElement().toString();
                String shape = stringTokenizer.nextElement().toString();

                switch (objectClass) {
                    case "InanimateObject":
                        type = stringTokenizer.nextElement().toString();
                        break;
                    case "Hamming":
                    case "Closest":
                    case "RandomStrat":
                        FOV= Integer.parseInt(stringTokenizer.nextElement().toString());
                        maxHops = Integer.parseInt(stringTokenizer.nextElement().toString());
                        break;
                    default:
                        System.out.println("Invalid line");
                        break;
                }

                x = Integer.parseInt(stringTokenizer.nextElement().toString());
                y = Integer.parseInt(stringTokenizer.nextElement().toString());

                switch (objectClass) {
                    case "InanimateObject": {
                        environment.addToEnvironment(new InanimateObject(colour, shape, x, y, type));
                        break;
                    }
                    case "Hamming": {
                        environment.addToEnvironment(new Hamming(colour, shape, x, y, FOV, maxHops));
                        break;
                    }
                    case "RandomStrat": {
                        environment.addToEnvironment(new RandomStrat(colour, shape, x, y, FOV, maxHops));
                        break;
                    }
                    case "Closest": {
                        environment.addToEnvironment(new Closest(colour, shape, x, y, FOV, maxHops));
                        break;
                    }
                    default:
                        System.out.println("Invalid Object\n");
                        break;
                }
            }
        }
    }

    /**
     * Method that writes to the log in each iteration the coordinates, the memory  and the perception of the agent.
     * @param agent Agent whose data we are writing.
     * @param iteration Number of iteration.
     * @throws IOException
     */
    public void writeToLog(Agent agent, int iteration) throws  IOException {
        String line;

        line = "Iteration " + iteration;
        writeLine(line);

        line =  "Coordinates: (" + agent.getCoords().getX() + ", " + agent.getCoords().getY() + ")";
        writeLine(line);

        line = "Perception: ";

        for (int j = 0; j < agent.getNextPossibleObjectsArray().size(); j++) {
            line += "Object " + agent.getNextPossibleObjectsArray().get(j).getId() + " ";
        }

        writeLine(line);

        line = "Memory: ";

        for(int i = 0; i < agent.getMemory().size(); i++) {
            line += "Object " + agent.getMemory().get(i).getId() + " ";
        }

        writeLine(line);
        writeLine("");
    }

    /**
     * Method that in the final iteration of an agent writes the final statistics, the total distance travelled and the number of learnt objects.
     * We don't print the number of different learnt objects because our agents never re-learn any object.
     * @param agent
     * @throws IOException
     */
    public void writeStatsToLog (Agent agent) throws IOException {
        String line;

        line = "Final Statistics";
        writeLine(line);

        line = "Distance Travelled = " + agent.getDistanceTravelled();
        writeLine(line);

        line = "Number of learnt objects = " + agent.getMemory().size();
        writeLine(line);

        writeLine("");
    }

    /**
     * Method that opens and reads all lines in a file while subsequently printing them in the screen.
     * @param fileName Name of the file that is going to be read.
     * @param log
     * @throws IOException
     */
    public void readAndPrintFile(String fileName,javax.swing.JTextArea log) throws IOException {
        openFileRead(fileName);

        while(fR.ready()) {
            String line = fR.readLine();
            log.append(line+"\n");
        }

        closeRead();
    }

}

