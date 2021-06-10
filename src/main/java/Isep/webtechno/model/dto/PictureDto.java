package Isep.webtechno.model.dto;

import lombok.Data;

@Data
public class PictureDto {
    private Integer id;
    private boolean isFromInternet = false;
    private String url;
}
