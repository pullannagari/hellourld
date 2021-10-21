package com.cloudflare.hellourld.service;

import com.cloudflare.hellourld.entity.URL;
import com.cloudflare.hellourld.exception.DateFormatException;
import com.cloudflare.hellourld.repository.HelloURLDRepository;
import com.cloudflare.hellourld.representation.URLRepresentation;
import com.cloudflare.hellourld.utils.Base62EncodeDecode;
import com.cloudflare.hellourld.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class HelloURLDService {

    @Autowired
    private HelloURLDRepository helloURLDRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloURLDService.class);

    public HelloURLDService(){ }

    public Page<URL> getUrls(Pageable pageable) {
        Page<URL> page = helloURLDRepository.findAll(pageable);
        return page;
    }

    public URL getURL(String shortUrl) {
        Long urlId = Base62EncodeDecode.convertShortUrltoId(shortUrl);
        Optional<URL> url = helloURLDRepository.findById(urlId);
        if (url.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            URLRepresentation urlRepresentation = modelMapper.map(url.get(), URLRepresentation.class);
            if (!DateUtils.isFutureDate(urlRepresentation.getExpirationDate())){
                deleteURL(shortUrl);
                throw new NoSuchElementException("Value has expired");
            }
            urlRepresentation.setAccessCount(urlRepresentation.getAccessCount()+1);
            urlRepresentation.setLastAccessDate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return updateURL(urlRepresentation);
        }
        return url.get();
    }

    public URL saveURL(URLRepresentation urlRep) throws DateFormatException {
        ModelMapper modelMapper = new ModelMapper();
        URL url = modelMapper.map(urlRep, URL.class);
        url.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        setExpirationDate(urlRep, url);
        URL maxUrl = helloURLDRepository.findFirstByOrderByIdDesc();
        Long id;
        if (maxUrl == null)
            id = 1L;
        else
            id = maxUrl.getId()+1;
        url.setId(id);
        url.setShortURL(Base62EncodeDecode.convertIdtoShortUrl(id));
        return helloURLDRepository.insert(url);
    }

    private void setExpirationDate(URLRepresentation urlRep, URL url) throws DateFormatException {
        if(urlRep.getExpirationDate() == null)
            url.setExpirationDate(LocalDateTime.MAX.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        else{
            try{
                LocalDateTime expDT = LocalDateTime.parse(url.getExpirationDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                url.setExpirationDate(expDT.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }catch (DateTimeParseException dtpe){
                String message = "Exception while parsing the expiration date into "+DateTimeFormatter.ISO_LOCAL_DATE_TIME+" format";
                LOGGER.error(message + dtpe.getMessage());
                throw new DateFormatException(message, dtpe);
            }
        }
    }

    public URL updateURL(URLRepresentation urlRep) {
        ModelMapper modelMapper = new ModelMapper();
        URL url = modelMapper.map(urlRep, URL.class);
        url.setLastAccessDate(new Date().toString());
        return helloURLDRepository.save(url);
    }

    public String deleteURL(String shortUrl) {
        Long urlId = Base62EncodeDecode.convertShortUrltoId(shortUrl);
        helloURLDRepository.deleteById(urlId);
        return shortUrl;
    }

}
