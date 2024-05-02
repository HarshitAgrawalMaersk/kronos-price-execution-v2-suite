package kronos.price.exe.regression.suite;


import org.apache.commons.cli.*;
import org.testng.TestNG;
import org.testng.collections.Lists;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // create Options object
        Options options = new Options();

        Option testNGConfigFile = Option.builder("t").longOpt("testNGConfigFile")
                .argName("testNGConfigFile")
                .hasArg()
                .required(true)
                .desc("set Test NG config file").build();
        options.addOption(testNGConfigFile);

        Option testDriverFile = Option.builder("c").longOpt("testDriverFile")
                .argName("testDriverFile")
                .hasArg()
                .required(true)
                .desc("set Test Driver config file").build();
        options.addOption(testDriverFile);



        CommandLine cmd;
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helper = new HelpFormatter();

        String testNGConfigFileLoc = "" ;
        String testDriverFileLoc = "" ;

        try {
            cmd = parser.parse(options, args);

            testNGConfigFileLoc = cmd.getOptionValue("testNGConfigFile");
            System.out.println("testNGConfigFileLoc = " + testNGConfigFileLoc) ;

            testDriverFileLoc = cmd.getOptionValue("testDriverFile");
            System.out.println("testDriverFileLoc = " + testDriverFileLoc) ;

            System.setProperty("testDriverFile" , testDriverFileLoc);



        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Usage:", options);
            System.exit(0);
        }

        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add(testNGConfigFileLoc);
        testng.setTestSuites(suites);
        testng.run();
    }
}
