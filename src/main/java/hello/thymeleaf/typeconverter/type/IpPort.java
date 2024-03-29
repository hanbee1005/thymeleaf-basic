package hello.thymeleaf.typeconverter.type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class IpPort {
    private String ip;
    private int port;
}
