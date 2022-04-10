package com.ppetrica.facepalm.backend.data;

import static com.ppetrica.facepalm.backend.data.UserData.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.ppetrica.facepalm.backend.persistence.FacepalmItem;

import lombok.experimental.UtilityClass;


@UtilityClass
public class DDBMapperData {
    public final String EXPECTED_PK = "USER#" + USERNAME;
    public final String EXPECTED_SK = "#METADATA#" + USERNAME;
    public final String EXPECTED_PHOTO_SK = "PHOTO"
        + "#" + com.ppetrica.facepalm.backend.data.ImageData.USERNAME 
        + "#" + com.ppetrica.facepalm.backend.data.ImageData.TIMESTAMP;

    public FacepalmItem buildValidUserItem() {
        FacepalmItem item = new FacepalmItem();
        
        item.setUsername(USERNAME);
        item.setPk(EXPECTED_PK);
        item.setSk(EXPECTED_SK);
        item.setAddress(ADDRESS);
        item.setEmail(EMAIL);
        item.setFollowers(FOLLOWERS);
        item.setFollowing(FOLLOWING);
        item.setInterests(INTERESTS);
        item.setBirthdate(BIRTHDAY);
        item.setName(NAME);
        item.setStatus(STATUS);
        item.setPinnedImage(PINNED_IMAGE);

        return item;
    }

    public Stream<FacepalmItem> provideInvalidUserItemsWithNullFields() {
        List<Consumer<FacepalmItem>> compromisers = List.of(
            (FacepalmItem item) -> item.setAddress(null),
            (FacepalmItem item) -> item.setEmail(null),
            (FacepalmItem item) -> item.setUsername(null),
            (FacepalmItem item) -> item.setName(null),
            (FacepalmItem item) -> item.setStatus(null),
            (FacepalmItem item) -> item.setPinnedImage(null),
            (FacepalmItem item) -> item.setBirthdate(null),
            (FacepalmItem item) -> item.setInterests(null)
        );

        return generateInvalidItemsFromCompromisers(compromisers);
    }


    public Stream<FacepalmItem> provideInvalidUserItemsWithInvalidFollowers() {
        List<Consumer<FacepalmItem>> compromisers = List.of(
            (FacepalmItem item) -> item.setFollowers(-1),
            (FacepalmItem item) -> item.setFollowing(-5)
        );

        return generateInvalidItemsFromCompromisers(compromisers);
    }

    private Stream<FacepalmItem> generateInvalidItemsFromCompromisers(List<Consumer<FacepalmItem>> compromisers) {
        return compromisers.stream().map((compromiser) -> {
            FacepalmItem item = buildValidUserItem();
            
            compromiser.accept(item);
            
            return item;
        });
    }

    public FacepalmItem builValidImageItem() {
        FacepalmItem item = new FacepalmItem();
        
        item.setUsername(USERNAME);
        item.setPk(EXPECTED_PK);
        item.setSk(EXPECTED_PHOTO_SK);
        item.setTimestamp(com.ppetrica.facepalm.backend.data.ImageData.TIMESTAMP);
        item.setLocation(com.ppetrica.facepalm.backend.data.ImageData.LOCATION);
        item.setReactions(ImmutableMap.of(
            "+1", com.ppetrica.facepalm.backend.data.ImageData.REACTIONS.getLikes(),
            "smiley", com.ppetrica.facepalm.backend.data.ImageData.REACTIONS.getSmileys(),
            "sunglasses", com.ppetrica.facepalm.backend.data.ImageData.REACTIONS.getSunglasses(),
            "heart", com.ppetrica.facepalm.backend.data.ImageData.REACTIONS.getHearts()
        ));

        return item;
    }
}
