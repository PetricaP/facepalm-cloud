package com.ppetrica.facepalm.backend.data;

import static com.ppetrica.facepalm.backend.data.UserData.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.ppetrica.facepalm.backend.persistence.FacepalmItem;

import lombok.experimental.UtilityClass;


@UtilityClass
public class DDBMapperData {
    public final String EXPECTED_PK = "USER#" + USERNAME;
    public final String EXPECTED_SK = "#METADATA#" + USERNAME;

    public FacepalmItem buildValidUserItem() {
        return FacepalmItem.builder()
            .username(USERNAME)
            .pk(EXPECTED_PK)
            .sk(EXPECTED_SK)
            .address(ADDRESS)
            .email(EMAIL)
            .followers(FOLLOWERS)
            .following(FOLLOWING)
            .interests(INTERESTS)
            .birthdate(BIRTHDAY)
            .name(NAME)
            .status(STATUS)
            .pinnedImage(PINNED_IMAGE)
            .build();
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
}
