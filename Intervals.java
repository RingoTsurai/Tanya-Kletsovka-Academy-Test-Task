import java.util.HashMap;
import java.util.Map;

public class Intervals {

    private final static String[] degrees = {"C", "D", "E", "F", "G", "A", "B"};
    private final static String[] notes = {"C", "C#/Dbb", "C##/Db", "D", "D#/Ebb", "D##/Eb", "E", "E#/Fb",
            "F", "F#/Gbb", "F##/Gb", "G", "G#/Abb", "G##/Ab", "A", "A#/Bbb", "A##/Bb", "B", "B#/Cb"};
    private final static String[] allowedNotesInInput = {"Cb", "C", "C#", "Db", "D", "D#", "Eb", "E", "E#",
            "Fb", "F", "F#", "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B", "B#"};
    private final static Map<String, Integer> intervals = new HashMap<>();
    //shift degrees = key index; shift notes = value
    static {
        intervals.put("", 0);
        intervals.put("m2", 1);
        intervals.put("M2", 2);
        intervals.put("m3", 3);
        intervals.put("M3", 4);
        intervals.put("P4", 5);
        intervals.put("P5", 7);
        intervals.put("m6", 8);
        intervals.put("M6", 9);
        intervals.put("m7", 10);
        intervals.put("M7", 11);
        intervals.put("P8", 12);
    }
    private final static int FIRST_ARRAY_INDEX = 0;
    private final static int SHIFT_BY_ONE = 1;
    private final static int MIN_SHIFT_DEGREES = 2;
    private final static String ASC = "asc";
    private final static String DSC = "dsc";

    public static String intervalConstruction(String[] args) {

        if(args.length < 2 || args.length > 3){
            throw new RuntimeException("Illegal number of elements in input array");
        }
        if (!isAllowedNotesInInput(args[1])) {
            throw new RuntimeException("Illegal note element in input array");
        }
        boolean isIntervalASC = isIntervalASC(args);

        int degreePosition = findDegreePositionInList(args[1]);
        if (degreePosition == -1) {
            throw new RuntimeException("Illegal degree element in input array");
        }
        int shiftSize = findShiftSizeInMap(args[0]);
        int newDegreePosition = countNewDegreePosition(isIntervalASC, degreePosition, shiftSize);

        int notePosition = findNotePositionInList(args[1]);
        int newNotePosition = countNewNotePosition(isIntervalASC, notePosition, shiftSize, args[0]);

        return findNewNoteInList(newNotePosition, newDegreePosition);
    }

    public static String intervalIdentification(String[] args) {

        if(args.length < 2 || args.length > 3){
            throw new RuntimeException("Illegal number of elements in input array");
        }
        if (!isAllowedNotesInInput(args[0]) || !isAllowedNotesInInput(args[1])) {
            throw new RuntimeException("Illegal note element in input array");
        }
        boolean isIntervalASC = isIntervalASC(args);

        int firstDegreePosition = findDegreePositionInList(args[0]);
        int secondDegreePosition = findDegreePositionInList(args[1]);
        if (firstDegreePosition == -1 || secondDegreePosition == -1) {
            throw new RuntimeException("Illegal degree element in input array");
        }
        int shiftBetweenDegreesSize = countShiftDegreesSize(isIntervalASC, firstDegreePosition, secondDegreePosition);

        int firstNotePosition = findNotePositionInList(args[0]);
        int secondNotePosition = findNotePositionInList(args[1]);
        if (firstNotePosition == -1 || secondNotePosition == -1) {
            throw new RuntimeException("Illegal note element in input array");
        }
        int shiftNoteSize = countShiftNoteSize(isIntervalASC, firstNotePosition, secondNotePosition, shiftBetweenDegreesSize);

        if (!intervals.containsValue(shiftNoteSize)) {
            throw new RuntimeException("Illegal shift note size value");
        }

        return findKeyByValueInMap(shiftNoteSize);
    }

    private static boolean isAllowedNotesInInput(String args) {
        boolean isAllowed = false;
        for (int i = 0; i < allowedNotesInInput.length; i++) {
            if (allowedNotesInInput[i].equals(args)) {
                isAllowed = true;
            }
        }
        return isAllowed;
    }

    private static boolean isIntervalASC(String[] args) {
        boolean isIntervalASC = true;
        if (args.length == 3) {
            if (args[2].equals(DSC)) {
                isIntervalASC = false;
            } else if (!args[2].equals(ASC)) {
                throw new RuntimeException("Illegal argument in input array");
            }
        }
        return isIntervalASC;
    }

    private static int findDegreePositionInList(String args) {
        int degreePosition = -1;
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i].equals(args.substring(0,1))) {
                degreePosition = i;
            }
        }
        return degreePosition;
    }

    private static int findShiftSizeInMap(String args) {
        int shiftSize;
        if (intervals.containsKey(args) && !args.equals("")) {
            shiftSize = Integer.parseInt(args.substring(1));
        } else {
            throw new RuntimeException("Illegal interval element in input array");
        }
        return shiftSize;
    }

    private static int countNewDegreePosition(boolean isIntervalASC, int degreePosition, int shiftSize) {
        int newDegreePosition;
        if (isIntervalASC) {
            newDegreePosition = degreePosition + (shiftSize - SHIFT_BY_ONE);
            //stay within the array
            if (newDegreePosition >= degrees.length) {
                newDegreePosition -= degrees.length;
            }
        } else {
            newDegreePosition = degreePosition - (shiftSize - SHIFT_BY_ONE);
            //stay within the array
            if (newDegreePosition < FIRST_ARRAY_INDEX) {
                newDegreePosition += degrees.length;
            }
        }
        return newDegreePosition;
    }

    private static int findNotePositionInList(String args) {
        int notePosition = -1;
        for (int i = 0; i < notes.length; i++) {
            if (!notes[i].contains("/")) {
                if (notes[i].equals(args)) {
                    notePosition = i;
                }
            } else {
                String[] semitones = notes[i].split("/");
                if (semitones[0].equals(args) || semitones[1].equals(args)) {
                    notePosition = i;
                }
            }
        }
        return notePosition;
    }

    private static int countNewNotePosition(boolean isIntervalASC, int notePosition, int shiftSize, String args) {
        int newNotePosition;
        if (isIntervalASC) {
            newNotePosition = notePosition + (shiftSize - SHIFT_BY_ONE) + intervals.get(args);
            //stay within the array
            if (newNotePosition >= notes.length) {
                newNotePosition -= notes.length;
            }
        } else {
            newNotePosition = notePosition - (shiftSize - SHIFT_BY_ONE) - intervals.get(args);
            //stay within the array
            if (newNotePosition < FIRST_ARRAY_INDEX) {
                newNotePosition += notes.length;
            }
        }
        return newNotePosition;
    }

    private static String findNewNoteInList(int newNotePosition, int newDegreePosition) {
        if (!notes[newNotePosition].contains("/")) {
            return notes[newNotePosition];

        } else {
            String[] semitones = notes[newNotePosition].split("/");
            if (semitones[0].substring(0, 1).equals(degrees[newDegreePosition])) {
                return semitones[0];
            } else {
                return semitones[1];
            }
        }
    }

    private static int countShiftDegreesSize(boolean isIntervalASC, int firstDegreePosition, int secondDegreePosition) {
        int shiftBetweenDegreesSize;
        if (isIntervalASC) {
            shiftBetweenDegreesSize = (secondDegreePosition - firstDegreePosition) + SHIFT_BY_ONE;
        } else {
            shiftBetweenDegreesSize = (firstDegreePosition - secondDegreePosition) + SHIFT_BY_ONE;
        }
        //stay within the array
        if (shiftBetweenDegreesSize < MIN_SHIFT_DEGREES) {
            shiftBetweenDegreesSize += degrees.length;
        }
        return shiftBetweenDegreesSize;
    }

    private static int countShiftNoteSize(boolean isIntervalASC, int firstNotePosition, int secondNotePosition,
                                          int shiftBetweenDegreesSize) {
        int shiftNoteSize;
        if (isIntervalASC) {
            shiftNoteSize = secondNotePosition - firstNotePosition - (shiftBetweenDegreesSize - SHIFT_BY_ONE);
        } else {
            shiftNoteSize = firstNotePosition - secondNotePosition - (shiftBetweenDegreesSize - SHIFT_BY_ONE);
        }
        //stay within the array
        if (shiftNoteSize <= FIRST_ARRAY_INDEX) {
            shiftNoteSize += notes.length;
        }
        return shiftNoteSize;
    }

    private static String findKeyByValueInMap(int shiftNoteSize) {
        String key = "";
        for (Map.Entry<String, Integer> entry : intervals.entrySet()) {
            if (entry.getValue().equals(shiftNoteSize)) {
                key = entry.getKey();
            }
        }
        return key;
    }
}
