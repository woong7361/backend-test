package kr.co.polycube.backendtest.util;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    /**
     * 입력한 파라미터에 해당하는 중복되지 않는 랜덤한 int 배열을 반환한다.
     *
     * @param startValue 시작 value이고 값에 포함된다.
     * @param endValue 끝 value이고 값에 포함된다..
     */
    public static List<Integer> getNotDuplicateRandomNumbers(int size, int startValue, int endValue) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        HashSet<Integer> numbers = new HashSet<>();

        while (numbers.size() < size) {
            numbers.add(random.nextInt(startValue, endValue + 1));
        }

        return numbers.stream().toList();
    }
}
