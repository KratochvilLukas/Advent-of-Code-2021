import java.nio.file.Path
import java.nio.file.Paths

class FileInputReader {

    static List<String> readInputFromFile(URI uri) {
        try {

            def file = new File(uri)

            // read text returned by server
            BufferedReader inReader = new BufferedReader(new FileReader(file));

            List<String> lines = new ArrayList<>()
            String line;
            while ((line = inReader.readLine()) != null) {
                lines.add(line)
            }
            inReader.close();
            return lines

        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
            throw e
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
            throw e
        }
    }

    static List<String> readRecordsAsSingleLineUntilSeparator(URI uri, String recordsSeparator) {
        return readRecordsAsSingleLineUntilSeparator(Paths.get(uri).toAbsolutePath().toString(), recordsSeparator)
    }

    static List<String> readRecordsAsSingleLineUntilSeparator(String filePath, String recordsSeparator) {
        try {
            Path path = Paths.get(filePath)

            // read text returned by server
            BufferedReader inReader = new BufferedReader(new FileReader(path.toFile()));

            List<String> lines = new ArrayList<>()
            int emptyLines = 0
            String currentRecordLine = "";
            String line;
            while ((line = inReader.readLine()) != null) {
                if (line == recordsSeparator) {
                    lines.add(currentRecordLine.trim())
                    currentRecordLine = ""
                    emptyLines++
                } else {
                    currentRecordLine += " " + line
                }

            }
            lines.add(currentRecordLine)
            inReader.close();
            return lines

        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
            throw e
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
            throw e
        }
    }

    static String optimizeResourceFile(String filePath) {
        return filePath.startsWith("/") ? filePath : "/${filePath}"
    }
}
