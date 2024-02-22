package hei.school.sarisary.service.sary;

import hei.school.sarisary.endpoint.rest.controller.sary.dto.ImageLinks;
import hei.school.sarisary.file.BucketComponent;
import hei.school.sarisary.repository.model.Image;
import hei.school.sarisary.repository.model.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final BucketComponent bucketComponent;

    @SneakyThrows
    public Optional<File> convertBW(String imageId, byte[] payload) {
        String originalFileKey = String.format("og_%s", imageId);
        String modifiedFileKey = String.format("md_%s", imageId);
        if (payload == null) {
            return Optional.empty();
        }
        File originalImage = File.createTempFile(originalFileKey, ".png");
        File modifiedImage = File.createTempFile(modifiedFileKey, ".png");
        Path originalFilePath = Path.of(originalImage.getAbsolutePath());

        Files.write(originalFilePath, payload, StandardOpenOption.WRITE);

        BufferedImage originalBuffer = ImageIO.read(originalImage);
        BufferedImage modifiedBuffer = new BufferedImage(originalBuffer.getWidth(), originalBuffer.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics modifyGraphics = modifiedBuffer.getGraphics();
        modifyGraphics.drawImage(originalBuffer, 0, 0, null);
        ImageIO.write(modifiedBuffer, "png", modifiedImage);
        imageRepository.save(new Image(
                imageId,
                originalFileKey,
                modifiedFileKey,
                Timestamp.from(Instant.now())
        ));
        bucketComponent.upload(originalImage, originalFileKey);
        bucketComponent.upload(modifiedImage, modifiedFileKey);
        return Optional.of(modifiedImage);
    }

    public ImageLinks getImages(String imageId) {
        Optional<Image> imageKeys = imageRepository.findById(imageId);
        if (imageKeys.isPresent()) {
            Image image = imageKeys.get();
            return new ImageLinks(
                    bucketComponent.presign(image.getOriginal(), Duration.ofMinutes(10)),
                    bucketComponent.presign(image.getModified(), Duration.ofMinutes(10))
            );
        }
        return null;
    }
}
