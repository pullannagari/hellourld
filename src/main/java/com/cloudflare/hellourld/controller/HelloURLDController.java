package com.cloudflare.hellourld.controller;

import com.cloudflare.hellourld.entity.URL;
import com.cloudflare.hellourld.exception.DateFormatException;
import com.cloudflare.hellourld.exception.NoOriginalURLException;
import com.cloudflare.hellourld.representation.URLRepresentation;
import com.cloudflare.hellourld.service.HelloURLDService;
import com.cloudflare.hellourld.utils.Links;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RepositoryRestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HelloURLDController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloURLDController.class);

    @Autowired
    private HelloURLDService helloURLDService;

    @GetMapping(path = Links.URLS)
    public ResponseEntity<?> getUrls(Pageable pageable) {
        LOGGER.info("URLController get urls: ");
        Page<URL> urlList = helloURLDService.getUrls(pageable);
        LOGGER.info("found "+urlList.getTotalElements()+" urls");
        return ResponseEntity.ok(urlList);
    }

    @GetMapping(path = Links.URL)
    public ResponseEntity<?> getURL(@PathVariable("id") String urlId) {
        try {
            LOGGER.info("URLController get: " + urlId);
            URL url = helloURLDService.getURL(urlId);
            URI uri = new URI(url.getOriginalURL());
            //TODO validation of URL before saving
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }catch (RuntimeException e) {
            String errorMsg = "; Error, urlId not found :" + urlId;
            LOGGER.info(errorMsg);
            return new ResponseEntity<>(e.getMessage()+errorMsg, HttpStatus.NOT_FOUND);
        } catch (URISyntaxException e) {
            String errorMsg = "; Error, Invalid original URL for short url:" + urlId;
            LOGGER.info(errorMsg);
            return new ResponseEntity<>(e.getMessage()+errorMsg, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PostMapping(path = Links.CREATE_URL)
    public ResponseEntity<?> createURL(@RequestBody URLRepresentation urlRepresentation){
        LOGGER.info("URLController create: " + urlRepresentation);
        try{
            if(urlRepresentation.getOriginalURL() == null){
                throw new NoOriginalURLException("Original URL is mandatory");
            }
            URL url = helloURLDService.saveURL(urlRepresentation);
            return ResponseEntity.ok(url);
        }catch (DateFormatException dateFormatException){
            LOGGER.error("URLController: Bad request; incorrect datetime format");
            return new ResponseEntity<>(dateFormatException.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (NoOriginalURLException noOriginalURLException){
            LOGGER.error("URLController: Bad request; original URL is required");
            return new ResponseEntity<>(noOriginalURLException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = Links.DELETE_URL)
    public ResponseEntity<?> deleteURL(@PathVariable("id") String urlId) {
        LOGGER.info("urlController delete: " + urlId);
        String result = helloURLDService.deleteURL(urlId);
        return ResponseEntity.ok(result);
    }

}
