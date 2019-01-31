import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.game.OutcomeCalculator;
import com.colinparrott.parallelsimulator.programs.ProgramFile;
import com.colinparrott.parallelsimulator.programs.ProgramFileReader;
import org.junit.Test;

import java.util.ArrayList;

public class ParserTests
{
    @Test
    public void testSingleAtoZRegex()
    {
        String s = "";
        boolean result = s.matches("^([a-zA-z])");
        System.out.println(result);
    }

//    @Test
//    public void testParseKeywords()
//    {
//        String[] parts = new String[]{"AS $R0 1"};
//        AssemblyParser parser = new AssemblyParser();
//        Pair<Program, Optional<String>> result = parser.parseAssemblyCode(parts);
//        System.out.println(result.getValue().get());
//    }

    @Test
    public void testParseProgramFileReader(){
        ProgramFileReader.readProgramFiles();
    }

    @Test
    public void testParseProgramReader(){
        ArrayList<Program> programs = ProgramFileReader.readPrograms();
        for(Program p : programs){
            System.out.println(p.getName());
        }

        for(Program p : programs){
            for(int id : p.getUsedThreadIDs()){
                for(Instruction i : p.getInstructionsForThread(id)){
                    System.out.println(i);
                }
            }

        }
    }

    @Test
    public void testParseProgramReaderCalculateOutcome()
    {

        ProgramFile file = null;
        ArrayList<ProgramFile> programFiles = ProgramFileReader.readProgramFiles();
        for (ProgramFile f : programFiles)
        {
            if (f.getName().equals("x++"))
            {
                file = f;
                break;
            }
        }

        int[] seq = file.getInterestingSequences()[1];
        Program p = file.generateProgram();
        int value = OutcomeCalculator.calculateVariableOutcome(MemoryLocation.x, seq, p.getInitialMemory(), p);
        System.out.println("Value of x: " + value);

    }
}
