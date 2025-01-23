/**
 * A implementation of controller class that determine
 * the operations to react with hash tables and graph
 * 
 * Note: 
 * String output should concatenated with StringBuilder or 
 * StringBuffer for using less heap memory. However, I
 * asked for permission on piazza but did not receive
 * permission or positive answer, so I used concatenation
 * instead.
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.11
 */

public class Controller {
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private Hash artistTable;
    private Hash songTable;
    private Graph fullGraph;

    // ~ Constructors ..........................................................
    //
    // ----------------------------------------------------------
    /**
     * Initialize two hash tables with the given capacity
     * 
     * @param initHashSize
     *        The starting capacity for the hash table
     */
    public Controller(int initHashSize) {
        this.artistTable = new Hash(initHashSize);
        this.songTable = new Hash(initHashSize);
        this.fullGraph = new Graph(initHashSize);
    }
    
    // ~ Public Method .........................................................
    //
    // ----------------------------------------------------------
    /**
     * Method to trigger insert operation to the hash table
     * 
     * @param artistName
     *        Input string for artist name
     * @param songTitle
     *        Input string for song title
     */
    public void insert(String artistName, String songTitle) {
        // Create graph nodes for artists and songs
        GraphNode artistNode = new GraphNode();
        GraphNode songNode = new GraphNode();
        
        // Check if the artists and songs passed in exist
        // in hash tables
        boolean isArtistFound = this.artistTable.find(artistName);
        boolean isSongFound = this.songTable.find(songTitle);
        
        String outputString = "";
        
        // Create temporary variable to check
        // the resizing of hash tables
        int previousCapacity;
        int currentCapacity;
        
        // If the artist is not found,
        if (!isArtistFound) {
            // add a graph node to graph
            artistNode = this.fullGraph.addNode();
            
            // Calculate capacity before insert
            previousCapacity = this.artistTable.getCapacity();   
            // Insert
            boolean isInserted = this.artistTable
                .insert(artistName, artistNode);
            currentCapacity = this.artistTable.getCapacity();
            
            // Check if resizing triggered
            if (previousCapacity < currentCapacity) {
                outputString += "Artist hash table size doubled.\n";
            }
            // Check the result of insertion
            if (isInserted) {
                outputString += String.format(
                    "|%s| is added to the Artist database.\n"
                    , artistName);
            }
            
        } 
        // If artist is found, get the value(GraphNode) from the Record
        else {
            int index = this.artistTable.findIndexOfRecord(artistName);
            artistNode = this.artistTable.getHashTable()[index].getValue();
        }
        
        // (Logic for the song hash table is the same as above)
        if (!isSongFound) {

            songNode = this.fullGraph.addNode();
            
            previousCapacity = this.songTable.getCapacity();
            boolean isInserted = this.songTable.insert(songTitle, songNode);
            currentCapacity = this.songTable.getCapacity();
            
            if (previousCapacity < currentCapacity) {
                outputString += "Song hash table size doubled.\n";
            }
            
            if (isInserted) {
                outputString += String.format(
                    "|%s| is added to the Song database.\n"
                    , songTitle);
            }

        }
        // If song is found
        else {
            int index = this.songTable.findIndexOfRecord(songTitle);
            songNode = this.songTable.getHashTable()[index].getValue();
        }
        
        // Start interacting with the graph
        // connect two graph nodes
        boolean addEdgeSuccess = this.fullGraph.addEdge(artistNode, songNode);
        
        // If edge is not added, that means both nodes already have connection
        if (!addEdgeSuccess) {
            outputString += 
                String.format(
                    "|%s<SEP>%s| duplicates a record already in the database.\n"
                    , artistName, songTitle);
        }

        // Final output
        System.out.print(outputString);
    }

    // ----------------------------------------------------------
    /**
     * Method to trigger remove operation to the hash table
     * 
     * @param type
     *        Type of database(song or artist)
     * @param token
     *        Artist name or song title
     */
    public void remove(String type, String token) {
        // Capitalized the input in order to match required output
        String typeCapitalized = type.substring(0, 1).toUpperCase() 
            + type.substring(1);

        if ("Artist".equals(typeCapitalized)) {
            // Get the artist from the hash table
            Record artistRemoved = this.artistTable.remove(token);
            
            // If artist does not exist
            if (artistRemoved == null) {
                System.out.println("|" + token + "| does not exist in the "
                    + typeCapitalized + " database.");
            }

            else {
                System.out.println("|" + artistRemoved.getKey()
                    + "| is removed from the " + typeCapitalized
                    + " database.");
                
                // Remove the artist node from graph
                GraphNode artistNode = artistRemoved.getValue();
                this.fullGraph.removeNode(artistNode);
            }
        }
        
        // (Logic for the song hash table is the same as above)
        if ("Song".equals(typeCapitalized)) {
            Record songRemoved = this.songTable.remove(token);
            // If song does not exist
            if (songRemoved == null) {
                System.out.println("|" + token + "| does not exist in the "
                    + typeCapitalized + " database.");
            }
            else {
                System.out.println("|" + songRemoved.getKey()
                    + "| is removed from the " + typeCapitalized
                    + " database.");
                
                // Remove node from graph
                GraphNode songNode = songRemoved.getValue();
                this.fullGraph.removeNode(songNode);
            }
        }
    }

    // ----------------------------------------------------------
    /**
     * Method to visualize the hash table and the graph
     * 
     * @param type
     *        Type of database(song or artist) or graph
     */
    public void print(String type) {

        if ("artist".equals(type)) {
            String artistResult = this.artistTable.print(type);
            System.out.println(artistResult);
        }
        if ("song".equals(type)) {
            String songResult = this.songTable.print(type);
            System.out.println(songResult);
        }
        if ("graph".equals(type)) {
            String graphResult = this.fullGraph.printWithUnionFind();
            System.out.println(graphResult);
        }
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for getting artist table
     * 
     * @return
     *         The hash table for artist name data
     */
    public Hash getArtistTable() {
        return this.artistTable;
    }

    // ----------------------------------------------------------
    /**
     * Setter to set artist table
     * 
     * @param artistTable
     *        Hash table for artist name data to be set
     */
    public void setArtistTable(Hash artistTable) {
        this.artistTable = artistTable;
    }

    // ----------------------------------------------------------
    /**
     * Getter for getting song table
     * 
     * @return
     *         T hash table for song title data
     */
    public Hash getSongTable() {
        return this.songTable;
    }

    // ----------------------------------------------------------
    /**
     * Setter to set song table
     * 
     * @param songTable
     *        Hash table for song title data to be set
     */
    public void setSongTable(Hash songTable) {
        this.songTable = songTable;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for getting the graph
     * @return 
     *         the fullGraph
     */
    public Graph getFullGraph() {
        return fullGraph;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for setting the graph
     * @param fullGraph 
     *        the fullGraph to be set
     */
    public void setFullGraph(Graph fullGraph) {
        this.fullGraph = fullGraph;
    }
    
}
