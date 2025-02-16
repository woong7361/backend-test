package kr.co.polycube.backendtest.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class RandomUtilsTest {
    @Nested
    @DisplayName("중복되지 않는 랜덤 수열 발급 테스트")
    public class NestedClass {
        @DisplayName("정상 처리")
        @ParameterizedTest
        @MethodSource("randomNumberInputArguments")
        public void success(int size, int startValue, int endValue) throws Exception {
            //given
            //when
            List<Integer> results = RandomUtils.getNotDuplicateRandomNumbers(size, startValue, endValue);

            //then
            Assertions.assertThat(results.size()).isEqualTo(size);
            Assertions.assertThat(results.stream().mapToInt(t -> t).max().getAsInt())
                    .isLessThanOrEqualTo(endValue);
            Assertions.assertThat(results.stream().mapToInt(t -> t).min().getAsInt())
                    .isGreaterThanOrEqualTo(startValue);

        }

        private static Stream<Arguments> randomNumberInputArguments() {
            return Stream.of(
                    Arguments.of(1, 4, 56),
                    Arguments.of(2, 6, 45),
                    Arguments.of(3, 10, 99),
                    Arguments.of(4, 1, 100),
                    Arguments.of(5, 4, 68),
                    Arguments.of(6, 9, 84)
            );
        }
    }
}