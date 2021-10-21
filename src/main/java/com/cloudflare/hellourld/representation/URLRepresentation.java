package com.cloudflare.hellourld.representation;

import lombok.Data;

@Data
public class URLRepresentation {
    private Long id;
    private String originalURL;
    private Long accessCount;
    private String description;
    private String createdDate;
    private String lastAccessDate;
    private String expirationDate;
    private String shortURL;
}
