package com.crcc.exam.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 对排序条件进行反序列化操作
 */
@Slf4j
public class SortDeserializer extends JsonDeserializer<Sort> {
    @Override
    public Sort deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) {
        try {
            log.debug(jsonParser.toString());
            /**
             * 对排序数据进行反序列化时，如果输入格式不对、或未录入将导致反序列化失败
             */
            ArrayNode node = jsonParser.getCodec().readTree(jsonParser);
            log.debug(node.toString());
            List<Sort.Order> orderList = new ArrayList<>();
            for (JsonNode obj : node) {
                orderList.add(new Sort.Order(
                        Sort.Direction.valueOf(obj.get("direction").asText().toUpperCase()),
                        obj.get("property").asText()));
            }
            Sort sort = Sort.by(orderList);
            return sort;
        } catch (Exception e) {
            return null;
        }
    }
}
