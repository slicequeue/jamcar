package com.slicequeue.jamcar.util;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * ParameterizedTest 에 사용할 Util 클래스
 * - JUnit 테스트에 필요한 반복 작업을 줄이는 용도의 static 클래스
 */
public class ParameterizedTestUtil {

    /**
     * Parameterized 테스트시 각종 인자 케이스에 사용할 Stream Arguments 생성
     * @param excludeFullCondition true: 인자 모두 있는 경우 제외  / false: 인자 모두 있는 경우 포함
     * @param args 케이스 만들 요청 인자
     * @return 인자의 갯수 만큼 인자의 유무 조합으로 Stream Arguments 생성
     */
    public static Stream<Arguments> getParamStreamArguments(boolean excludeFullCondition, Object ...args) {
        int edgeRight  = (excludeFullCondition) ? 2 : 1;
        int length = args.length;
        // 인자 갯수에 따른 경우의 수 생성 - 2진법 덧샘 활용
        String s = Integer.toBinaryString(0);
        List<String> caseKeys = new ArrayList<>();
        String format = "%0"+args.length+"d";

        caseKeys.add(String.format(format, Integer.valueOf(s)));
        for (int i = 0; i < (Math.pow(2, args.length) - edgeRight); i++) {
            s = addBinaryPlus(s, "1");
            caseKeys.add(String.format(format, Integer.valueOf(s)));
        }
        // 인자 생성
        List<Arguments> stream = new ArrayList<>();
        for (String caseKey : caseKeys) {
            Object[] params = new Object[length + 1];
            params[0] = caseKey;
            for (int i = 0; i < length; i++) {
                char c = caseKey.charAt(i);
                params[i+1] = ((c == '1') ? args[i] : null);
            }
            stream.add(() -> params);
        }
        return stream.stream();
    }

    private static String addBinaryPlus(String a, String b) {
        int aInt = Integer.parseInt(a, 2);
        int bInt = Integer.parseInt(b, 2);
        return Integer.toBinaryString(addBin(aInt, bInt));
    }

    private static int addBin(int a, int b) {
        if(b == 0) return a;
        int sum = a^b;
        int carry = (a&b) << 1;
        return addBin(sum, carry);
    }

}
