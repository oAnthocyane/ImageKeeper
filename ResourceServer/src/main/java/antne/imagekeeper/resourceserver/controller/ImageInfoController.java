package antne.imagekeeper.resourceserver.controller;

import antne.imagekeeper.resourceserver.model.data.ImageInfoDTO;
import antne.imagekeeper.resourceserver.response.ResponseHandler;
import antne.imagekeeper.resourceserver.service.image.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageInfoController {

    private final ImageInfoService imageInfoService;

    @Autowired
    public ImageInfoController(ImageInfoService imageInfoService) {
        this.imageInfoService = imageInfoService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody ImageInfoDTO imageInfoDTO) throws IOException {
        long id = imageInfoService.save(imageInfoDTO);
        return ResponseHandler.responseBuilder("Image was added", HttpStatus.CREATED, id);
    }

    @GetMapping("/findByUniqPhraseAndUser/{uniqPhrase}/user/{userId}")
    public ResponseEntity<Object> findByUniqPhraseAndUser(@PathVariable String uniqPhrase, @PathVariable long userId)
            throws IOException{
        byte[] photo = imageInfoService.findByUniqPhraseAndUser(uniqPhrase, userId);
        return ResponseHandler.responseBuilder("Image was found", HttpStatus.OK, photo);
    }
}
