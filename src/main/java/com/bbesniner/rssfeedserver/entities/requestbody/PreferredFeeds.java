package com.bbesniner.rssfeedserver.entities.requestbody;

import lombok.Data;

import java.util.List;

@Data
public class PreferredFeeds {

    private List<String> preferredFeedsUuid;

}
