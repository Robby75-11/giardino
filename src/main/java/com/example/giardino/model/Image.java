package com.example.giardino.model;

import com.cloudinary.Cloudinary;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "images")
@Entity
@Data
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private String publicId;


}
