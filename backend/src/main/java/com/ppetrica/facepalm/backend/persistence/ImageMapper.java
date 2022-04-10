package com.ppetrica.facepalm.backend.persistence;


import com.ppetrica.facepalm.backend.data.Reactions;
import com.ppetrica.facepalm.backend.entities.Image;
import org.mapstruct.Mapper;

import javax.validation.Valid;
import java.util.Map;


@Mapper
public class ImageMapper {
    @Valid
    public Image facepalmItemToImage(FacepalmItem item) {
        Map<String, Integer> reactions = item.getReactions();

        return Image.builder()
            .username(item.getUsername())
            .timestamp(item.getTimestamp())
            .location(item.getLocation())
            .reactions(
                new Reactions(
                    reactions.get("+1"),
                    reactions.get("smiley"),
                    reactions.get("sunglasses"),
                    reactions.get("heart")
                )
            )
            .build();
    }
}
