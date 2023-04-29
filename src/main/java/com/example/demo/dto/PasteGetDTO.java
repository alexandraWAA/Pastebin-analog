package com.example.demo.dto;
import com.example.demo.model.Paste;
import lombok.Data;
@Data
public class PasteGetDTO {

    private String title;
    private String link;

    public static PasteGetDTO from(Paste paste){
        PasteGetDTO pasteGetDTO = new PasteGetDTO();
        pasteGetDTO.setTitle(paste.getTitle());
        pasteGetDTO.setLink("/my-awesome-pastebin.tld/" + paste.getId());
        return pasteGetDTO;
    }
}