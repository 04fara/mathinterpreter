import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    private static List<String> getInput(String path) throws IOException {
        return new BufferedReader(new FileReader(path)).lines().collect(Collectors.toList());
    }

    private static void setOutput(String path, ArrayList<Object> result) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (int i = 0; i < result.size(); i++)
            writer.write(result.get(i).toString() + ((i < result.size() - 1) ? "\n" : ""));
        writer.close();
    }

    private static void run(List<String> source, boolean withJSON) throws IOException {
        ArrayList<Object> ans = new ArrayList<>();
        Expression expression;
        for (String line : source) {
            if (line.equals("")) continue;
            line = line.replaceAll(" ", "");
            expression = new Parser(new Scanner(line).scanTokens()).parse();
            if (withJSON)
                ans.add(String.format("Source: %s%nResult: %s%n%s%n\n##########################################\n",
                        line, new Interpreter().interpret(expression), new JSONPrinter().printJSON(expression)));
            else ans.add(new Interpreter().interpret(expression));
        }
        setOutput("testOutput.txt", ans);
    }

    public static void main(String[] args) throws IOException {
        //answer without JSON
        //run(getInput("input.txt"), false);
        //answer with JSON
        run(getInput("testInput.txt"), true);
    }
}