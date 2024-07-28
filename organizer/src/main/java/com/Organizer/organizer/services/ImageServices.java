package com.Organizer.organizer.services;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ImageServices {

    private final Cloudinary cloudinary;
    private static final Logger logger = LoggerFactory.getLogger(ImageServices.class);

    @Autowired
    public ImageServices(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String image(MultipartFile mfile, String filename) {
        try {
            byte[] data = mfile.getBytes();

            logger.info("Uploading image with filename: " + filename);

            Map uploadResult = cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename
            ));

            logger.info("Upload successful: " + uploadResult);

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrlFromPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(500)
                                .height(500)
                                .crop("fill"))
                .generate(publicId);
    }
}
