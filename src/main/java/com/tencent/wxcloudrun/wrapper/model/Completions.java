package com.tencent.wxcloudrun.wrapper.model;

import lombok.Data;

import java.util.List;

@Data
public class Completions {

    public String id;
    public String object;
    public int created;
    public String model;
    public List<Choice> choices;
    public Usage usage;

    @Data
    public static class Choice {
        public String text;
        public int index;
        public Object logprobs;
        public String finish_reason;
    }

    @Data
    public static class Usage {
        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
    }


}
