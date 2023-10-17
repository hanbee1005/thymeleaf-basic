package hello.thymeleaf.typeconverter.formatter;

import hello.thymeleaf.typeconverter.converter.IpPortToStringConverter;
import hello.thymeleaf.typeconverter.converter.StringToIpPortConverter;
import hello.thymeleaf.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {
    @Test
    public void formattingConversionServiceTest() {
        // given
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        conversionService.addFormatter(new MyNumberFormatter());

        // when
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        String s = conversionService.convert(1000, String.class);
        Number n = conversionService.convert("1,000", Number.class);

        // then
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(s).isEqualTo("1,000");
        assertThat(n).isEqualTo(1000L);
    }
}
