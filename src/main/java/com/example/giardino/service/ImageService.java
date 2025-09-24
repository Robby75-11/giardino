package com.example.giardino.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.giardino.model.Image;
import com.example.giardino.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    ImageRepository imageRepository;

    public Image uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        Image image = new Image();
               image.setUrl(uploadResult.get("url").toString());
                image.setPublicId(uploadResult.get("public_id").toString());


        return imageRepository.save(image);
    }

    // ✅ Recupera tutte le immagini
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    // ✅ Recupera immagine per ID
    public java.util.Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    // ✅ Elimina immagine sia da Cloudinary che dal DB
    public void deleteImage(Long id) throws IOException {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Immagine non trovata"));

        cloudinary.uploader().destroy(image.getPublicId(), ObjectUtils.emptyMap());
        imageRepository.delete(image);
    }
}