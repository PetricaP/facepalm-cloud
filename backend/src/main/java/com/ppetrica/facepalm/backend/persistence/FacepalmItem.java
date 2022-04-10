package com.ppetrica.facepalm.backend.persistence;


import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
@DynamoDBTable(tableName="")
public class FacepalmItem {
    @Getter(onMethod = @__({@DynamoDBHashKey}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDBRangeKey}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String address;
    
    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String email;
    
    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String username;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String name;
    
    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String status;
    
    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private List<String> interests;
    
    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private int followers;
    
    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private int following;
    
    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String pinnedImage;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String birthdate;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String followingUser;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String followedUser;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String timestamp;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String location;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private Map<String, Integer> reactions;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String reactingUser;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String photo;

    @Getter(onMethod = @__({@DynamoDBAttribute}))
    private String reactionType;
}
