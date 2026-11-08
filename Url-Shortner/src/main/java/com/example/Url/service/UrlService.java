package com.example.Url.service;



import com.example.Url.model.UrlEntity;
import com.example.Url.repository.UrlRepository;
import com.example.Url.util.Base62Encoder;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String shortenUrl(String originalUrl) {
        UrlEntity entity = new UrlEntity();
        entity.setOriginalUrl(originalUrl);
        
        // Save to DB to get the auto-incremented ID
        entity = repository.save(entity);
        
        // Use Base62 to create the short code
        return Base62Encoder.encode(entity.getId());
    }

    public String getOriginalUrl(String shortCode) {
        long id = Base62Encoder.decode(shortCode);
        
        UrlEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));
                
        // Increment click count
        entity.setClickCount(entity.getClickCount() + 1);
        repository.save(entity);

        return entity.getOriginalUrl();
    }
}
