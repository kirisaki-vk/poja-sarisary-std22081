package hei.school.sarisary.endpoint.rest.controller.sary.dto;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImageLinks {
  private URL original_url;
  private URL transformed_url;
}
