import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * Test cases for Command Processor
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.11
 */

public class CommandProcessorTest extends TestCase {
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private CommandProcessor testProcessor;
    private Controller controller;
    private String inputPath;
    private String outputPath;
    
    // ~ Set up ............................................................
    //
    /**
     * Sets up the tests that follow. In general, used for initialization
     * 
     */
    public void setUp() {
        this.inputPath = "./solutionTestData/P1_test_sampleInput.txt";
        this.outputPath = "./solutionTestData/P1_test_sampleOutput.txt";
        this.controller = new Controller(10);
        this.testProcessor = 
            new CommandProcessor(this.inputPath, this.controller);
        this.testProcessor.setFile(this.inputPath);

    }
 
    // ~ Test Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * Test input case 1
     * @throws Exception
     *         Throw Exception if file does not exist 
     */
    public void testSample1() throws Exception {
        
        assertNotNull(this.testProcessor.getController());
        assertNotNull(this.controller);
        
        this.testProcessor.parseFile(inputPath);
        String outputContent = new String(Files.readAllBytes(
            Paths.get(this.outputPath)), StandardCharsets.UTF_8);
        String outputHistory = systemOut().getHistory();
        assertFuzzyEquals(outputContent, outputHistory);
    }
    
    // ----------------------------------------------------------
    /**
     * Test input case 2
     * @throws Exception
     *         Throw Exception if file does not exist
     */
    public void testSample2() throws Exception {
        this.inputPath = "./solutionTestData/P1_sampleInput.txt";
        this.outputPath = "./solutionTestData/P1_sampleOutput.txt";
        this.controller = new Controller(10);
        this.testProcessor = 
            new CommandProcessor(this.inputPath, this.controller);
        this.testProcessor.setFile(this.inputPath);
        this.testProcessor.parseFile(inputPath);
        String outputContent = new String(Files.readAllBytes(
            Paths.get(this.outputPath)), StandardCharsets.UTF_8);
        String outputHistory = systemOut().getHistory();
        assertFuzzyEquals(outputContent, outputHistory);
    }
    
    // ----------------------------------------------------------
    /**
     * Test input case 3
     * @throws Exception
     *         Throw Exception if file does not exist
     */
    public void testSample3() throws Exception {
        this.inputPath = "./solutionTestData/sampleInput2.txt";
        this.outputPath = "./solutionTestData/sampleOutput2.txt";
        this.controller = new Controller(10);
        this.testProcessor = 
            new CommandProcessor(this.inputPath, this.controller);
        this.testProcessor.setFile(this.inputPath);
        this.testProcessor.parseFile(inputPath);
        String outputContent = new String(Files.readAllBytes(
            Paths.get(this.outputPath)), StandardCharsets.UTF_8);
        String outputHistory = systemOut().getHistory();
        assertFuzzyEquals(outputContent, outputHistory);
    }
    
}
