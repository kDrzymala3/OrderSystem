package logic;

import java.util.List;

public class WrongFile {
    private String filename;
    private List<Integer> lines;

    public WrongFile(String filename, List<Integer> lines) {
        this.filename = filename;
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "Filename: " + filename +
                " Lines/Element: " + lines;
    }
}
