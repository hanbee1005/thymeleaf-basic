package hello.thymeleaf.typeconverter;

import hello.thymeleaf.typeconverter.converter.IntegerToStringConverter;
import hello.thymeleaf.typeconverter.converter.IpPortToStringConverter;
import hello.thymeleaf.typeconverter.converter.StringToIntegerConverter;
import hello.thymeleaf.typeconverter.converter.StringToIpPortConverter;
import hello.thymeleaf.typeconverter.formatter.MyNumberFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class ConverterWebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // formatter 보다 converter가 우선 순위가 높아서 주석 처리
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());

        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        registry.addFormatter(new MyNumberFormatter());
    }
}
