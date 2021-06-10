package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.PictureDto;
import Isep.webtechno.model.entity.Picture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PictureConverter {
    ModelMapper modelMapper = new ModelMapper();

    public PictureDto toDto(Picture picture) {
        PictureDto pictureDto = new PictureDto();
        modelMapper.map(picture, pictureDto);

        return pictureDto;
    }

    public List<PictureDto> toDto(List<Picture> pictures) {
        return pictures.stream().map(this::toDto).collect(Collectors.toList());
    }
}
