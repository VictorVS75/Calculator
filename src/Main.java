import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String calc(String arExpression) {
        String result = "";
        int resultInt = 0;
        String arOperation;
        String arg1;
        String arg2;
        boolean isArgsRoman = false;
        int arg1Int = 0;
        int arg2Int = 0;


        if(isMoreThanOne(arExpression)) {
            result = "Ошибка. Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)";
        } else {
            Pattern p = Pattern.compile("[\\+\\-\\*\\/]");
            Matcher m = p.matcher(arExpression);
            if (!m.find()) {
                result = "Ошибка. Нет ни одного оператора (+, -, /, *)";
            } else {
                arOperation = arExpression.substring(m.start(), m.end());
                arg1 = arExpression.substring(0, m.start()).trim();
                arg2 = arExpression.substring(m.end()).trim();

                if (isRomanNumber(arg1) && isRomanNumber(arg2)) {
                    isArgsRoman = true;
                    arg1Int = romanNumberToInt(arg1);
                    arg2Int = romanNumberToInt(arg2);
                } else {
                    try {
                        arg1Int = Integer.parseInt(arg1);
                        arg2Int = Integer.parseInt(arg2);
                    } catch (Exception e) {
                        result = "Ошибка. Используются одновременно разные системы счисления или веденные операнды не являются числами";
                    }
                }

                if (result.length() == 0 && (arg1Int < 1 || arg1Int > 10)) {
                    result = "Ошибка. Первый аргумент не число от 1 до 10.";
                }
                if (result.length() == 0 && (arg2Int < 1 || arg2Int > 10)) {
                    result = "Ошибка. Второй аргумент не число от 1 до 10.";
                }

                switch (arOperation) {
                    case "+" -> resultInt = arg1Int + arg2Int;
                    case "-" -> resultInt = arg1Int - arg2Int;
                    case "*" -> resultInt = arg1Int * arg2Int;
                    case "/" -> resultInt = arg1Int / arg2Int;
                }

                if (result.length() == 0) {
                    if (isArgsRoman) {
                        if (resultInt <= 0) {
                            result = "Ошибка. В римской системе нет отрицательных чисел и 0";
                        } else {
                            result = intToRomanNumber(arg1Int) + " " + arOperation + " " + intToRomanNumber(arg2Int) + " = " + intToRomanNumber(resultInt);
                        }
                    } else {
                        result = arg1Int + " " + arOperation + " " + arg2Int + " = " + resultInt;
                    }
                }
            }
        }
        return result;
    }

    public static boolean isMoreThanOne(String arExpression) {
        Pattern p = Pattern.compile("[\\+\\-\\*\\/][IVXLCDM0-9\\s]+[\\+\\-\\*\\/]");
        Matcher m = p.matcher(arExpression);
        return m.find();
    }

    public static boolean isRomanNumber(String romanNumber) {
        Pattern p = Pattern.compile("[^IVXLCDM]");
        Matcher m = p.matcher(romanNumber);
        return !m.find();
    }

    public static int romanNumberToInt(String romanNumber) {
        int result = 0;
        if (isRomanNumber(romanNumber)) {
            Map<Character, Integer> numbersMap = new HashMap<>();
            numbersMap.put('I', 1);
            numbersMap.put('V', 5);
            numbersMap.put('X', 10);
            numbersMap.put('L', 50);
            numbersMap.put('C', 100);
            numbersMap.put('D', 500);
            numbersMap.put('M', 1000);

            for (int i = 0; i < romanNumber.length(); i++) {
                char ch = romanNumber.charAt(i);
                if (i > 0 && numbersMap.get(ch) > numbersMap.get(romanNumber.charAt(i - 1))) {
                    result = result + numbersMap.get(ch) - 2 * numbersMap.get(romanNumber.charAt(i - 1));
                } else {
                    result = result + numbersMap.get(ch);
                }
            }
        }
        return result;
    }

    public static String intToRomanNumber(int number) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanLiterals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder romanNumber = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number = number - values[i];
                romanNumber.append(romanLiterals[i]);
            }
        }
        return romanNumber.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите арифметическое выражение:");
        System.out.print(calc(scanner.nextLine()));
    }
}