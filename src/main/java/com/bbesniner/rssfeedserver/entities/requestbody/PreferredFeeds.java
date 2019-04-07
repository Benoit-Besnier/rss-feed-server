package com.bbesniner.rssfeedserver.entities.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferredFeeds {

    private List<String> preferredFeeds;

}
