package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 3, 2, 3};
        int[] arr2 = new int[]{9, 8, 8};
        System.out.println("minValue:");
        System.out.println(Arrays.toString(arr1) + " : " + minValue(arr1));
        System.out.println(Arrays.toString(arr2) + " : " + minValue(arr2));

        System.out.println();

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 6);
        System.out.println("oddOrEven:");
        System.out.println(list1 + " : " + oddOrEven(list1));
        System.out.println(list2 + " : " + oddOrEven(list2));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (left, right) -> left * 10 + right);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream()
                .filter(v -> (sum + v) % 2 != 0)
                .collect(Collectors.toList());
    }
}
