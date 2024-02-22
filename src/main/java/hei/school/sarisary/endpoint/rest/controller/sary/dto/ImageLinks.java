package hei.school.sarisary.endpoint.rest.controller.sary.dto;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImageLinks {
  private URL originalUrl;
  private URL transformedUrl;
}
