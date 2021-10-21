package com.cloudflare.hellourld.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class URL {
    @Id
    private Long id;
    private String originalURL;
    private Long accessCount;
    private String description;
    private String createdDate;
    private String lastAccessDate;
    private String expirationDate;
    private String shortURL;
}
