import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


class Main {
    private static final Map<Character, Integer> ROMAN_TO_ARABIC_MAP = new HashMap<>();
    private static final TreeMap<Integer, String> ARABIC_TO_ROMAN_MAP = new TreeMap<>();
    public static boolean isArabic = true;
    static {
        ROMAN_TO_ARABIC_MAP.put('I', 1);
        ROMAN_TO_ARABIC_MAP.put('V', 5);
        ROMAN_TO_ARABIC_MAP.put('X', 10);
        ROMAN_TO_ARABIC_MAP.put('L', 50);
        ROMAN_TO_ARABIC_MAP.put('C', 100);
        ROMAN_TO_ARABIC_MAP.put('D', 500);
        ROMAN_TO_ARABIC_MAP.put('M', 1000);

        ARABIC_TO_ROMAN_MAP.put(1, "I");
        ARABIC_TO_ROMAN_MAP.put(4, "IV");
        ARABIC_TO_ROMAN_MAP.put(5, "V");
        ARABIC_TO_ROMAN_MAP.put(9, "IX");
        ARABIC_TO_ROMAN_MAP.put(10, "X");
        ARABIC_TO_ROMAN_MAP.put(40, "XL");
        ARABIC_TO_ROMAN_MAP.put(50, "L");
        ARABIC_TO_ROMAN_MAP.put(90, "XC");
        ARABIC_TO_ROMAN_MAP.put(100, "C");
        ARABIC_TO_ROMAN_MAP.put(400, "CD");
        ARABIC_TO_ROMAN_MAP.put(500, "D");
        ARABIC_TO_ROMAN_MAP.put(900, "CM");
        ARABIC_TO_ROMAN_MAP.put(1000, "M");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите выражение (или 'exit' для выхода): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Программа завершена.");
                break;
            }
            String result = calc(input);
            System.out.println("Результат: " + result);
        }
        scanner.close();
    }

    public static int romanToInteger(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char currentRoman = roman.charAt(i);
            int currentValue = ROMAN_TO_ARABIC_MAP.get(currentRoman);

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }
        return result;
    }

    public static String arabicToRoman(int num) {
        StringBuilder roman = new StringBuilder();

        int floorKey;
        while (num > 0) {
            floorKey = ARABIC_TO_ROMAN_MAP.floorKey(num);
            roman.append(ARABIC_TO_ROMAN_MAP.get(floorKey));
            num -= floorKey;
        }
        return roman.toString();
    }

    static String calc(String input) {
        try {
            String[] tokens = input.split(" ");
            int num1;
            int num2;
            if (tokens.length != 3)
                throw new IllegalArgumentException("Неверное количество аргументов");
            try {
                num1 = Integer.parseInt(tokens[0]);
                num2 = Integer.parseInt(tokens[2]);
                isArabic = true;
            } catch (NumberFormatException e){
                num1 = parseNumber(tokens[0]);
                num2 = parseNumber(tokens[2]);
                isArabic = false;
            }

            if (num1 > 10 || num1 < 1 || num1 > 10 || num1 < 1)
                throw new ArithmeticException("Числа больше или меньше допустимых");

            System.out.println("Выражение:" + num1 + tokens[1] + num2);
            char operator = tokens[1].charAt(0);

            int result;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0)
                        throw new ArithmeticException("Деление на ноль");
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неверный оператор");
            }
            if (isArabic)
                return String.valueOf(result);
            else if (result > 0){
                return arabicToRoman(result);
            }
            else
                throw new IllegalArgumentException("Результатом работы калькулятора с римскими числами могут быть только положительные числа");
        } catch (NumberFormatException e) {
            return "Ошибка: неверный формат числа";
        } catch (IllegalArgumentException | ArithmeticException e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    static int parseNumber(String input) {
        char ch = input.charAt(0);
        if (ROMAN_TO_ARABIC_MAP.containsKey(ch))
            return romanToInteger(input);
        else
            throw new IllegalArgumentException("Неверный формат числа");
    }
}
