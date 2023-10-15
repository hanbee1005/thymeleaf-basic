package hello.thymeleaf.typeconverter.controller;

import hello.thymeleaf.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/type-converter")
public class HelloController {
    @GetMapping("/hello-v1")
    public String hello1(HttpServletRequest request) {
        String data = request.getParameter("data");
        int intValue = Integer.parseInt(data);
        log.info("intValue = {}", intValue);
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String hello2(@RequestParam Integer data) {
        log.info("data = {}", data);
        return "ok";
    }

    @GetMapping("/ip-port")
    public String hello2(@RequestParam IpPort ipPort) {
        log.info("IpPort IP = {}", ipPort.getIp());
        log.info("IpPort PORT = {}", ipPort.getPort());
        return "ok";
    }
}
