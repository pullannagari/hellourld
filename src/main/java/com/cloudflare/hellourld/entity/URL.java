package com.cloudflare.hellourld.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class URL {
    @Id
    private String id;
    private String originalURL;
    private Integer access_count;
    private String description;
    private String createdDate;
    private String createdBy;
    private String updatedDate;
    private String updatedBy;
}
