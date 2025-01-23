import java.util.Scanner;
import java.io.File;

// -------------------------------------------------------------------------
/**
 * This is a class that can read in command line input and parse txt file
 * 
 * Source Code Citation:
 * 
 * Title: Unknown
 * Author: OpenDSA Project Contributors
 * Date: Last updated on Aug. 14, 2024
 * Code Version: Unknown
 * Availability: Module 2.7. Reading Input (from Files or Otherwise)
 * Note: The structure and detailed implementation all came from the
 * cited source code. Some revision was implemented based on project
 * specific specs and goals.
 *
 * @author Guann-Luen Chen
 * @version 2024.09.03
 */

public class CommandProcessor {
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private Controller controller;
    private String file;
    
    // ~ Constructors ..........................................................
    //
    /**
     * Instantiate command line processor to handle text file
     * @param file
     *        File name
     * @param controller
     *        Controller object
     */
    public CommandProcessor(String file, Controller controller) {
        this.file = file;
        this.controller = controller;
    }
    
    // ~ Public Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * Method to directly run the parsing function
     * @throws Exception 
     *         Throw out exception
     */
    public void execute() throws Exception {
        this.parseFile(this.file);
        
    }
    
    // ----------------------------------------------------------
    /**
     * Method to parse input text file and give instruction to controller
     * based on the content of the file
     * 
     * @param filename
     *        Input file
     */
    public void parseFile(String filename) throws Exception {
        
        try (Scanner sc = new Scanner(new File(filename))) {

            Scanner scancmd; // Declare two scanners one to read the file 
                             // one to read the text pulled from the file

            while (sc.hasNextLine()) { // While we have text to read

                String line = sc.nextLine(); // Get our next line
                scancmd = new Scanner(line); // Create a scanner from this line
                String cmd = scancmd.next(); // Get the first word (the command)
                                             // on each line
                String type;

                switch (cmd) {

                    case "insert":// In the case of insert change our delimiter
                                  // from white space to <SEP>
                        scancmd.useDelimiter("<SEP>");
                        
                        // Get the artist name before <SEP>
                        String artistName = scancmd.next().trim();
                        // Get the song after <SEP>
                        String songTitle = scancmd.next().trim();
                        
                        this.controller.insert(artistName, songTitle);
                        break;

                    case "remove":
                        type = scancmd.next(); // Get the mode of deletion
                                               // artist/song
                        
                        // Since both artist titles and song titles have spaces
                        // get the rest of the line for the song/artist name
                        String token = scancmd.nextLine().trim();

                        switch (type) {
                            
                            case "artist":
                                this.controller.remove(type, token);
                                break;
                            
                            case "song":
                                this.controller.remove(type, token);
                                break;
                            
                            default: // Error bad token
                                System.out.println("Error bad remove type "
                                    + type);
                                break;
                        }
                        break;

                    case "print": // Print command
                        type = scancmd.next(); // get the type of print command

                        switch (type) {
                            case "artist":
                                this.controller.print(type);
                                break;

                            case "song":
                                this.controller.print(type);
                                break;

                            case "graph":
                                this.controller.print(type);
                                break;

                            default:
                                System.out.println("Error bad type");
                                break;
                        }
                        break;

                    default:
                        System.out.println("Unrecognized input");
                        break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ----------------------------------------------------------    
    /**
     * Getter to get the controller object
     * @return
     *         Controller object
     */
    public Controller getController() {
        return controller;
    }
    
    // ----------------------------------------------------------   
    /**
     * Setter to set controller object
     * @param controller
     *        Controller object
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    // ----------------------------------------------------------   
    /**
     * Getter to get the name of the file
     * @return
     *         File name
     */
    public String getFile() {
        return file;
    }
    
    // ----------------------------------------------------------   
    /**
     * Setter to set name of the file
     * @param file
     *        File name
     */
    public void setFile(String file) {
        this.file = file;
    }
    
}
