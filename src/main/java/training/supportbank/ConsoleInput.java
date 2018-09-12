package training.supportbank;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleInput
{
    static String listRegex = "\\w*\\W(.*)";

    public static void readInput()
    {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine())
        {
            String lineOfText = scanner.nextLine();
            try
            {
                useInput(lineOfText);
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
                Main.LOGGER.error(e);
            }
        }
    }

    static void useInput(String input) throws Exception
    {
        String[] inputs = input.split("\\s");

        switch(inputs[0].toLowerCase()) {
            case "list":
                if (inputs[1].equalsIgnoreCase("all")) {
                    Account.printAllAccounts();
                } else {
                    Pattern pattern = Pattern.compile(listRegex);
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.find()) {
                        String name = matcher.group(1);
                        if (name.isEmpty()) throw new Exception("No name given.");
                        else {
                            Account foundAccount = Account.findAccountFromName(name);
                            if (foundAccount == null) throw new Exception("No account found for " + name + ".");
                            else {
                                Transaction.printAllTransactions(foundAccount);
                            }
                        }
                    }
                }
        }
    }
}
