package hei.school.sarisary.endpoint.rest.controller.sary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@AllArgsConstructor
@Getter
@Setter
public class ImageLinks {
    private URL originalUrl;
    private URL transformedUrl;
}
