package com.asgla.util;

import net.sf.json.JSONObject;

public interface IDispatchable {

    /**
     * Dispatches a JSON message to this object.
     * <p>
     *
     * @param jsonObject         the JSON object
     */
    void dispatch(JSONObject jsonObject);

}
