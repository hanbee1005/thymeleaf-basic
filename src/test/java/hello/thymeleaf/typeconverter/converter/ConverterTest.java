package hello.thymeleaf.typeconverter.converter;

import hello.thymeleaf.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {
    @Test
    public void stringToInteger() {
        // given
        StringToIntegerConverter converter = new StringToIntegerConverter();

        // when
        Integer result = converter.convert("10");

        // then
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void integerToString() {
        // given
        IntegerToStringConverter converter = new IntegerToStringConverter();

        // when
        String result = converter.convert(10);

        // then
        assertThat(result).isEqualTo("10");
    }

    @Test
    public void stringToIpPort() {
        // given
        String source = "127.0.0.1:8080";
        StringToIpPortConverter converter = new StringToIpPortConverter();

        // when
        IpPort result = converter.convert(source);

        // then
        assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));

    }

    @Test
    public void ipPortToString() {
        // given
        IpPort source = new IpPort("127.0.0.1", 8080);
        IpPortToStringConverter converter = new IpPortToStringConverter();

        // when
        String result = converter.convert(source);

        // then
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }
}
