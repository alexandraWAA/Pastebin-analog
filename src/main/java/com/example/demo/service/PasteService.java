package com.example.demo.service;

import com.example.demo.dto.CreateDTO;
import com.example.demo.dto.PasteDTO;
import com.example.demo.dto.PasteGetDTO;
import com.example.demo.dto.UrlDTO;
import com.example.demo.enums.Status;
import com.example.demo.exception.InvalidParametersExeption;
import com.example.demo.exception.PasteNotFoundException;
import com.example.demo.model.Paste;
import com.example.demo.repository.PasteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PasteService {
    private final PasteRepository pasteRepository;
    private final String alphabet;

    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
        this.alphabet = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890!@#$%&";
    }

    public UrlDTO create(CreateDTO createDTO) {
        Paste paste = createDTO.to();
        paste.setDateCreated(Instant.now());
        paste.setStatus(createDTO.getStatus());
        paste.setDateExpired(createDTO.getExpirationTime().getDate());
        paste.setId(generateId(8));
        createDTO.setLink("/my-awesome-pastebin.tld/" + paste.getId());
        pasteRepository.save(paste);
        return UrlDTO.fromCreateDTO(createDTO);
    }

    public List<PasteGetDTO> getTenLast() {

        return pasteRepository.findTop10ByStatusAndDateExpiredIsAfterOrderByDateCreatedDesc(Status.PUBLIC, Instant.now())
                .stream()
                .map(PasteGetDTO::from)
                .collect(Collectors.toList());
    }

    public PasteDTO getById(String id){
        if(id==null){
            throw new InvalidParametersExeption();
        }
        Paste paste = pasteRepository.findPastById(id);
        if (paste == null){
            throw new PasteNotFoundException();
        }
        return PasteDTO.from(paste);
    }

    public List<PasteDTO> search(String title, String body) {
        return pasteRepository.findAllByTitleOrBody(Status.PUBLIC, title, body)
                .stream()
                .map(PasteDTO::from)
                .collect(Collectors.toList());
    }

    private String generateId(int lengthLink){
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for(int i = 0; i < lengthLink; i++){
            id.append(alphabet.toCharArray()[random.nextInt(alphabet.length())]);
        }
        return id.toString();
    }
}
