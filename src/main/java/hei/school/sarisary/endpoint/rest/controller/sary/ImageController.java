package hei.school.sarisary.endpoint.rest.controller.sary;

import hei.school.sarisary.endpoint.rest.controller.sary.dto.ImageLinks;
import hei.school.sarisary.service.sary.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService service;

    @GetMapping("/black-and-white/{id}")
    public ImageLinks getImages(@PathVariable String id) {
        return service.getImages(id);
    }

    @PutMapping(
            value = "/black-and-white/{id}",
            consumes = {
                    MediaType.IMAGE_PNG_VALUE
            }
    )
    public ResponseEntity<Object> blackAndWhite(@PathVariable String id, @RequestBody byte[] image) {
        Optional<File> modifiedImage = service.convertBW(id, image);
        return ResponseEntity.ok().build();
    }
}
