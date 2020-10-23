import java.util.HashMap;
import java.util.Map;

public class Intervals {

    private final static String[] degrees = {"C", "D", "E", "F", "G", "A", "B"};
    private final static String[] notes = {"C", "C#/Dbb", "C##/Db", "D", "D#/Ebb", "D##/Eb", "E", "E#/Fb",
            "F", "F#/Gbb", "F##/Gb", "G", "G#/Abb", "G##/Ab", "A", "A#/Bbb", "A##/Bb", "B", "B#/Cb"};
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

    public static String intervalConstruction(String[] args) {

        if(args.length < 2 || args.length > 3){
            throw new RuntimeException("Illegal number of elements in input array");
        }

        boolean isIntervalASC = true;
        if (args.length == 3) {
            if (args[2].equals("dsc")) {
                isIntervalASC = false;
            } else if (!args[2].equals("asc")) {
                throw new RuntimeException("Illegal argument in input array");
            }
        }
        System.out.println(isIntervalASC);

        int degreePosition = -1;
        //находим позицию degree в массиве
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i].equals(args[1].substring(0,1))) {
                degreePosition = i;
            }
        }
        System.out.println("degreePosition="+degreePosition);
        //проверка на правильный входной элемент
        if (degreePosition == -1) {
            throw new RuntimeException("Illegal degree element in input array");
        }

        //узнаем на сколько элементов сдвигать
        int shiftSize;
        if (intervals.containsKey(args[0]) && !args[0].equals("")) {
            shiftSize = Integer.parseInt(args[0].substring(1));
        } else {
            throw new RuntimeException("Illegal interval element in input array");
        }
        System.out.println("shiftSize="+shiftSize);
        //находим сдвиг на новый degree
        int newDegreePosition;
        if (isIntervalASC) {
            newDegreePosition = degreePosition + (shiftSize - SHIFT_BY_ONE);
            //остаемся в пределах массива
            if (newDegreePosition >= degrees.length) {
                newDegreePosition -= degrees.length;
            }
        } else {
            System.out.println("is dsc");
            newDegreePosition = degreePosition - (shiftSize - SHIFT_BY_ONE);
            System.out.println("newDegreePosition="+newDegreePosition);
            //остаемся в пределах массива
            if (newDegreePosition < FIRST_ARRAY_INDEX) {
                newDegreePosition += degrees.length;
            }
        }
        System.out.println("newDegreePosition="+newDegreePosition);


        int notePosition = -1;
        for (int i = 0; i < notes.length; i++) {
            if (!notes[i].contains("/")) {
                if (notes[i].equals(args[1])) {
                    notePosition = i;
                    System.out.println("notePosition="+notePosition);
                }
            } else {
                //  System.out.println(notes[i] + " contains(/)");
                String[] semitones = notes[i].split("/");
                if (semitones[0].equals(args[1]) || semitones[1].equals(args[1])) {
                    notePosition = i;
                    System.out.println("notePosition="+notePosition);
                }
            }
        }

        //находим на сколько полутонов сдвиг
        System.out.println("intervals.get(args[0])="+intervals.get(args[0]));
        //находим новую позицию из 10
        int newNotePosition;
        if (isIntervalASC) {
            newNotePosition = notePosition + (shiftSize - SHIFT_BY_ONE) + intervals.get(args[0]);
            System.out.println("newNotePosition="+newNotePosition);
            //остаемся в пределах массива
            if (newNotePosition >= notes.length) {
                newNotePosition -= notes.length;
            }
        } else {
            newNotePosition = notePosition - (shiftSize - SHIFT_BY_ONE) - intervals.get(args[0]);
            System.out.println("newNotePosition="+newNotePosition);
            //остаемся в пределах массива
            if (newNotePosition < FIRST_ARRAY_INDEX) {
                newNotePosition += notes.length;
            }
        }

        System.out.println("newNotePosition="+newNotePosition);
        //если позиция имеет одну ноту, то возвращаем её
        if (!notes[newNotePosition].contains("/")) {
            System.out.println(notes[newNotePosition] + " !contains(/)");
            return notes[newNotePosition];

        } else {
            //если позиция имеет две ноту, то выбираем нужную
            System.out.println(notes[newNotePosition] + " contains(/)");
            //получаем две ноты отдельно
            String[] semitones = notes[newNotePosition].split("/");
            //если первый знак ноты == найденой ступени
            if (semitones[0].substring(0, 1).equals(degrees[newDegreePosition])) {
                System.out.println("semitones[0]="+semitones[0]);
                return semitones[0];

            } else {
                System.out.println("semitones[1]="+semitones[1]);
                return semitones[1];
            }
        }


    }

    public static String intervalIdentification(String[] args) {

        if(args.length < 2 || args.length > 3){
            throw new RuntimeException("Illegal number of elements in input array");
        }

        boolean isIntervalASC = true;
        if (args.length == 3) {
            if (args[2].equals("dsc")) {
                isIntervalASC = false;
            } else if (!args[2].equals("asc")) {
                throw new RuntimeException("Illegal argument in input array");
            }
        }
        System.out.println(isIntervalASC);

        int firstDegreePosition = -1;
        //находим позицию degree в массиве
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i].equals(args[0].substring(0,1))) {
                firstDegreePosition = i;
            }
        }
        System.out.println("firstDegreePosition="+firstDegreePosition);

        int secondDegreePosition = -1;
        //находим позицию degree в массиве
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i].equals(args[1].substring(0,1))) {
                secondDegreePosition = i;
            }
        }
        System.out.println("secondDegreePosition="+secondDegreePosition);

        //проверка на правильный входной элемент
        if (firstDegreePosition == -1 || secondDegreePosition == -1) {
            throw new RuntimeException("Illegal degree element in input array");
        }

        int shiftDegreesSize = -1;
        if (isIntervalASC) {
            System.out.println("is asc");
            shiftDegreesSize = (secondDegreePosition - firstDegreePosition) + SHIFT_BY_ONE;
            System.out.println("shiftDegreesSize="+shiftDegreesSize);
            //остаемся в пределах массива
            if (shiftDegreesSize < MIN_SHIFT_DEGREES) {
                shiftDegreesSize += degrees.length;
            }
        } else {
            System.out.println("is dsc");
            shiftDegreesSize = (firstDegreePosition - secondDegreePosition) + SHIFT_BY_ONE;
            System.out.println("shiftDegreesSize="+shiftDegreesSize);
            //остаемся в пределах массива
            if (shiftDegreesSize < MIN_SHIFT_DEGREES) {
                shiftDegreesSize += degrees.length;
            }
        }
        System.out.println("shiftDegreesSize="+shiftDegreesSize);

        int firstNotePosition = -1;
        for (int i = 0; i < notes.length; i++) {
            if (!notes[i].contains("/")) {
                if (notes[i].equals(args[0])) {
                    firstNotePosition = i;
                    System.out.println("firstNotePosition="+firstNotePosition);
                }
            } else {
                String[] semitones = notes[i].split("/");
                if (semitones[0].equals(args[0]) || semitones[1].equals(args[0])) {
                    firstNotePosition = i;
                    System.out.println("firstNotePosition="+firstNotePosition);
                }
            }
        }

        int secondNotePosition = -1;
        for (int i = 0; i < notes.length; i++) {
            if (!notes[i].contains("/")) {
                if (notes[i].equals(args[1])) {
                    secondNotePosition = i;
                    System.out.println("secondNotePosition="+secondNotePosition);
                }
            } else {
                String[] semitones = notes[i].split("/");
                if (semitones[0].equals(args[1]) || semitones[1].equals(args[1])) {
                    secondNotePosition = i;
                    System.out.println("secondNotePosition="+secondNotePosition);
                }
            }
        }

        if (firstNotePosition == -1 || secondNotePosition == -1) {
            throw new RuntimeException("Illegal note element in input array");
        }

        int shiftNoteSize = -1;
        if (isIntervalASC) {
            System.out.println("is asc");
            shiftNoteSize = secondNotePosition - firstNotePosition - (shiftDegreesSize - SHIFT_BY_ONE);
            System.out.println("shiftNoteSize="+shiftNoteSize);
            //остаемся в пределах массива
            if (shiftNoteSize <= FIRST_ARRAY_INDEX) {
                shiftNoteSize += notes.length;
            }
        } else {
            System.out.println("is dsc");
            shiftNoteSize = firstNotePosition - secondNotePosition - (shiftDegreesSize - SHIFT_BY_ONE);
            System.out.println("shiftNoteSize="+shiftNoteSize);
            //остаемся в пределах массива
            if (shiftNoteSize <= FIRST_ARRAY_INDEX) {
                shiftNoteSize += notes.length;
            } else if (shiftNoteSize <= FIRST_ARRAY_INDEX) {

            }
        }
        System.out.println("shiftNoteSize="+shiftNoteSize);

        if (!intervals.containsValue(shiftNoteSize)) {
            throw new RuntimeException("Illegal shift note size value");
        }

        String result = new String();
        System.out.println("result="+result);
        for (Map.Entry<String, Integer> entry : intervals.entrySet()) {
            if (entry.getValue().equals(shiftNoteSize)) {
                result = entry.getKey();
                System.out.println("result="+result);
            }
        }

        return result;
    }
}
