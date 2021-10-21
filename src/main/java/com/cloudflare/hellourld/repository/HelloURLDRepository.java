package com.cloudflare.hellourld.repository;

import com.cloudflare.hellourld.entity.URL;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloURLDRepository extends MongoRepository<URL, Long> {

    URL findFirstByOrderByIdDesc();
}
