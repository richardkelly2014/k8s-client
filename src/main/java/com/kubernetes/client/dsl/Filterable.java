package com.kubernetes.client.dsl;

import com.kubernetes.api.model.LabelSelector;
import com.kubernetes.api.model.ObjectReference;

import java.util.Map;

/**
 * 过滤操作
 *
 * @param <T>
 */
public interface Filterable<T> {

    T withLabels(Map<String, String> labels);

    T withoutLabels(Map<String, String> labels);

    T withLabelIn(String key, String... values);

    T withLabelNotIn(String key, String... values);

    T withLabel(String key, String value);

    T withLabel(String key);

    T withoutLabel(String key, String value);

    T withoutLabel(String key);

    T withFields(Map<String, String> labels);

    T withField(String key, String value);

    T withoutFields(Map<String, String> fields);

    T withoutField(String key, String value);

    T withLabelSelector(LabelSelector selector);

    /**
     * Filter with the object that this event is about.
     *
     * @param objectReference {@link ObjectReference} for providing information of referred object
     * @return filtered resource
     */
    T withInvolvedObject(ObjectReference objectReference);
}
