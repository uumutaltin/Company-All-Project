package com.works.properties;

import com.works.entities.Options;
import com.works.entities.Survey;
import lombok.Data;

import java.util.List;
@Data
public class SurveyOptionsLayer {

    private Survey sid;
    private List<Options> options;
}
